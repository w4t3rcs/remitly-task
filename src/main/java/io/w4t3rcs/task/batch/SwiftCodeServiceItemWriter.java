package io.w4t3rcs.task.batch;

import io.w4t3rcs.task.dto.MessageSwiftCodeResponse;
import io.w4t3rcs.task.dto.SwiftCodeRequest;
import io.w4t3rcs.task.service.SwiftCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SwiftCodeServiceItemWriter implements ItemWriter<SwiftCodeRequest> {
    private final SwiftCodeService swiftCodeService;

    @Override
    public void write(Chunk<? extends SwiftCodeRequest> chunk) throws Exception {
        chunk.forEach(item -> {
            MessageSwiftCodeResponse response = swiftCodeService.createSwiftCode(item);
            log.debug("SwiftCodeServiceItemWriter.write() response: {}", response);
        });
    }
}
