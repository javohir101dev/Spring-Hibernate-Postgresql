package uz.nt.springhibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseDto <D> {
    private boolean success;
    private Integer code;
    private String message;
    private D data;

}
