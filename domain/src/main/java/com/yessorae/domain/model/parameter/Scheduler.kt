package com.yessorae.domain.model.parameter

/**
 * DDPMScheduler
 * DDIMScheduler
 * PNDMScheduler
 * LMSDiscreteScheduler
 * EulerDiscreteScheduler
 * EulerAncestralDiscreteScheduler
 * DPMSolverMultistepScheduler
 * HeunDiscreteScheduler
 * KDPM2DiscreteScheduler
 * DPMSolverSinglestepScheduler
 * KDPM2AncestralDiscreteScheduler
 * UniPCMultistepScheduler
 * DDIMInverseScheduler
 * DEISMultistepScheduler
 * IPNDMScheduler
 * KarrasVeScheduler
 * ScoreSdeVeScheduler
 *
 * TODO:: Enum 으로 변경, SR-N displayName 맵핑
 * DPM++ SDE Karras는 실사모델에서 자주사용되며, 좀 더 디테일함, 시간 가장 오래걸림
 * Euler a(Ancestral) 가 가장 많이 사용되는 듯
 * Ancestral sampler 는 각 스텝별로 노이즈를 추가한다. 따라서 스텝을 아무리 올려도 수렴하지 않는다.
 */
data class Scheduler(
    val id: String,
    val displayName: String
)

val schedulers = listOf(
    "DDPMScheduler",
    "DDIMScheduler",
    "PNDMScheduler",
    "LMSDiscreteScheduler",
    "EulerDiscreteScheduler",
    "EulerAncestralDiscreteScheduler",
    "DPMSolverMultistepScheduler",
    "HeunDiscreteScheduler",
    "KDPM2DiscreteScheduler",
    "DPMSolverSinglestepScheduler",
    "KDPM2AncestralDiscreteScheduler",
    "UniPCMultistepScheduler",
    "DDIMInverseScheduler",
    "DEISMultistepScheduler",
    "IPNDMScheduler",
    "KarrasVeScheduler",
    "ScoreSdeVeScheduler"
)