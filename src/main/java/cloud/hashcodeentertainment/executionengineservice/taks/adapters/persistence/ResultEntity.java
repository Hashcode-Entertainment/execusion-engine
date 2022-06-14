package cloud.hashcodeentertainment.executionengineservice.taks.adapters.persistence;

import cloud.hashcodeentertainment.executionengineservice.taks.domain.TaskRunStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "results")
@Getter
@Setter
@NoArgsConstructor
public class ResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private TaskRunStatus runStatus;

    private Long exitCode;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "result_id")
    private List<LogEntity> logs = new ArrayList<>();
}
