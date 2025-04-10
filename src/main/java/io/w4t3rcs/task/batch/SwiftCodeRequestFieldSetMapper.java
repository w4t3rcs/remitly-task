package io.w4t3rcs.task.batch;

import io.w4t3rcs.task.dto.SwiftCodeRequest;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class SwiftCodeRequestFieldSetMapper implements FieldSetMapper<SwiftCodeRequest> {
    @Override
    public SwiftCodeRequest mapFieldSet(FieldSet fieldSet) throws BindException {
        String code = fieldSet.readString(1);
        String bankName = fieldSet.readString(3);
        String address = fieldSet.readString(4);
        String countryIso2 = fieldSet.readString(0).toUpperCase();
        String countryName = fieldSet.readString(6).toUpperCase();
        return new SwiftCodeRequest(code, bankName, address, countryIso2, countryName, false);
    }
}
