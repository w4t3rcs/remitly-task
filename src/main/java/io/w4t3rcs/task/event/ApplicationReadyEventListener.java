package io.w4t3rcs.task.event;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationReadyEventListener {
    private final JobLauncher jobLauncher;
    private final Job csvImportJob;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void listen(ApplicationReadyEvent event) {
        jobLauncher.run(csvImportJob, new JobParameters());
    }
}
