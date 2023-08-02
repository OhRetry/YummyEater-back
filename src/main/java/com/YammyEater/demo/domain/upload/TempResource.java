package com.YammyEater.demo.domain.upload;


import com.YammyEater.demo.domain.BaseTimeEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TEMP_RESOURCE")
public class TempResource{
    @Id
    @Column(name = "resource_key")
    private String key;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;
}
