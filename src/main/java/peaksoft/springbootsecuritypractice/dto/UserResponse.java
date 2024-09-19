package peaksoft.springbootsecuritypractice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class UserResponse {

    private Long id;
    private String name;
    private String password;
    private List<String> roles;




}
