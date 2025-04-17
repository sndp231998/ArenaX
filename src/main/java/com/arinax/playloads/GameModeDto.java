package com.arinax.playloads;



import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class GameModeDto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer modeId;

    @Column(nullable = false)
    private String modeName; // e.g., 2v2, 4v4

}