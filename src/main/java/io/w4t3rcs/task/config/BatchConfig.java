package io.w4t3rcs.task.config;

import io.w4t3rcs.task.batch.SwiftCodeRequestFieldSetMapper;
import io.w4t3rcs.task.dto.SwiftCodeRequest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@EnableBatchProcessing
@Configuration
public class BatchConfig {
    @Bean
    public FlatFileItemReader<SwiftCodeRequest> reader(SwiftCodeRequestFieldSetMapper mapper) {
        return new FlatFileItemReaderBuilder<SwiftCodeRequest>()
                .linesToSkip(1)
                .name("csvReader")
                .resource(new ClassPathResource("csv/data.csv"))
                .delimited()
                .delimiter(",")
                .names("countryISO2", "code", "type", "bankName", "address", "townName", "countryName", "timeZone")
                .fieldSetMapper(mapper)
                .build();
    }

    @Bean
    public Step csvImportStep(ItemReader<SwiftCodeRequest> reader, ItemWriter<SwiftCodeRequest> writer,
                              JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("csvImportStep", jobRepository)
                .<SwiftCodeRequest, SwiftCodeRequest>chunk(10, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Job csvImportJob(Step csvImportStep, JobRepository jobRepository) {
        return new JobBuilder("csvImportJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(csvImportStep)
                .end()
                .build();
    }
}
