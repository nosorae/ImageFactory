package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

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
 */
data class SchedulerOption(
    override val id: String,
    override val title: StringModel,
    override val selected: Boolean
) : Option {
    companion object
}

fun SchedulerOption.Companion.initialValues(): List<SchedulerOption> {
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
        "KDPM2DiscreteScheduler", // 이게 DPM++ SDE Karras 랑 가까운 것 같은데?
        "DPMSolverSinglestepScheduler",
        "KDPM2AncestralDiscreteScheduler", // 이게 DPM++ SDE Karras 랑 가까운 것 같은데?
        "UniPCMultistepScheduler",
        "DDIMInverseScheduler",
        "DEISMultistepScheduler",
        "IPNDMScheduler",
        "KarrasVeScheduler",
        "ScoreSdeVeScheduler"
    ).mapIndexed { index, scheduler ->
        SchedulerOption(
            id = scheduler,
            title = TextString(scheduler),
            selected = index == 0
        )
    }
}
