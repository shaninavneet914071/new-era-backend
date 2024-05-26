package com.nsh.customerservice.exceptionhandler.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.nsh.customerservice.constantdata.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor(force = true)
public class ExceptionResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstant.ZONED_DATE_TIME_FORMAT)
    private final ZonedDateTime timestamp;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Throwable throwable;
    private final HttpStatus httpStatus;
    private final String msg;

}
