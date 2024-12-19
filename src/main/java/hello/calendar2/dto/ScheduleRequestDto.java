package hello.calendar2.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotNull
    private Long userId;
}
