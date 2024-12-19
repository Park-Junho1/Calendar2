package hello.calendar2.service;

import hello.calendar2.dto.ScheduleRequestDto;
import hello.calendar2.dto.ScheduleResponseDto;
import hello.calendar2.entity.Schedule;
import hello.calendar2.entity.User;
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
                .orElseThrow(() -> new RuntimeException("User not found"));

        Schedule schedule = Schedule.builder()
                .title(scheduleRequestDto.getTitle())
                .description(scheduleRequestDto.getDescription())
                .user(user)
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    public List<ScheduleResponseDto> getSchedulesByUserId(Long userId) {
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);
        return schedules.stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        schedule.setTitle(scheduleRequestDto.getTitle());
        schedule.setDescription(scheduleRequestDto.getDescription());
        scheduleRepository.save(schedule);
        return new ScheduleResponseDto(schedule);
    }

    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule already exists");
        }
        scheduleRepository.deleteById(id);
    }
}
