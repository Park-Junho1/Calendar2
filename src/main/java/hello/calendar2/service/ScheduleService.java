package hello.calendar2.service;

import hello.calendar2.dto.ScheduleRequestDto;
import hello.calendar2.dto.ScheduleResponseDto;
import hello.calendar2.entity.Schedule;
import hello.calendar2.entity.User;
import hello.calendar2.exception.CustomException;
import hello.calendar2.exception.ErrorCode;
import hello.calendar2.repository.ScheduleRepository;
import hello.calendar2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        User user = userRepository.findById(scheduleRequestDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Schedule schedule = Schedule.builder()
                .title(scheduleRequestDto.getTitle())
                .description(scheduleRequestDto.getDescription())
                .user(user)
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    public List<ScheduleResponseDto> getSchedulesByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        List<Schedule> schedules = scheduleRepository.findByUserId(userId);
        return schedules.stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        schedule.update(scheduleRequestDto.getTitle(), scheduleRequestDto.getDescription());

        scheduleRepository.save(schedule);
        return new ScheduleResponseDto(schedule);
    }

    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new CustomException(ErrorCode.SCHEDULE_NOT_FOUND);
        }
        scheduleRepository.deleteById(id);
    }
}
