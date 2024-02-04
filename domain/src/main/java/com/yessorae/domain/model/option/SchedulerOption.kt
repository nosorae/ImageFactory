package com.yessorae.domain.model.option

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
 * TODO:: Enum 으로 변경
 */
data class SchedulerOption(
    override val id: String,
    override val title: String,
    override val selected: Boolean
) : Option {
    companion object
}

fun SchedulerOption.Companion.initialValues(lastSchedulerId: String? = null): List<SchedulerOption> {
    // DPM++ SDE Karras는 실사모델에서 자주사용되며, 좀 더 디테일함, 시간 가장 오래걸림
    // Euler a(Ancestral) 가 가장 많이 사용되는 듯
    // Ancestral sampler 는 각 스텝별로 노이즈를 추가한다. 따라서 스텝을 아무리 올려도 수렴하지 않는다.
    return listOf(
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
    ).mapIndexed { index, scheduler ->
        SchedulerOption(
            id = scheduler,
            title = scheduler,
            selected = if (lastSchedulerId != null) {
                lastSchedulerId == scheduler
            } else {
                index == 0
            }
        )
    }
}
