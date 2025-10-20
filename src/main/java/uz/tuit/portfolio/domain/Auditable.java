package uz.tuit.portfolio.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime updatedAt;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private User createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private User updatedBy;

}
