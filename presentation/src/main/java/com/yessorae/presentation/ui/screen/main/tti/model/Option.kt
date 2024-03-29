package com.yessorae.presentation.ui.screen.main.tti.model

import com.yessorae.domain.model.parameter.EmbeddingsModel
import com.yessorae.domain.model.parameter.LoRaModel
import com.yessorae.domain.model.parameter.Model
import com.yessorae.domain.model.parameter.Prompt
import com.yessorae.domain.model.parameter.SDModel
import com.yessorae.domain.model.parameter.Scheduler

abstract class Option(open val selected: Boolean)

abstract class TextOption(
    override val selected: Boolean,
    open val displayName: String
) : Option(selected = selected)

abstract class ModelOption(
    override val selected: Boolean,
    open val model: Model
) : Option(selected = selected)

data class PromptOption(
    val prompt: String,
    val positive: Boolean,
    override val selected: Boolean = false
) : TextOption(selected = selected, displayName = prompt)

fun Prompt.asOption(): PromptOption {
    return PromptOption(
        prompt = prompt,
        positive = positive
    )
}

fun PromptOption.asDomainModel(): Prompt {
    return Prompt(
        prompt = prompt,
        positive = positive
    )
}


data class SDModelOption(
    override val model: SDModel,
    override val selected: Boolean = false,
) : ModelOption(
    selected = selected,
    model = model
)

fun SDModel.asOption(): SDModelOption {
    return SDModelOption(model = this)
}

data class LoRaModelOption(
    override val model: LoRaModel,
    override val selected: Boolean = false,
) : ModelOption(
    selected = selected,
    model = model
)

fun LoRaModel.asOption(): LoRaModelOption {
    return LoRaModelOption(model = this)
}

data class EmbeddingsModelOption(
    override val model: EmbeddingsModel,
    override val selected: Boolean = false
) : ModelOption(
    selected = selected,
    model = model
)

fun EmbeddingsModel.asOption(): EmbeddingsModelOption {
    return EmbeddingsModelOption(model = this)
}

data class SchedulerOption(
    val scheduler: Scheduler,
    override val displayName: String = scheduler.displayName,
    override val selected: Boolean = false,
) : TextOption(
    selected = selected,
    displayName = displayName
) {
    companion object {
        val initialScheduler = Scheduler.schedulers.mapIndexed { index, value ->
            SchedulerOption(
                scheduler = value,
                displayName = value.displayName,
                selected = index == 0
            )
        }
    }
}

fun Scheduler.asOption(selected: Boolean): SchedulerOption {
    return SchedulerOption(
        scheduler = this,
        displayName = displayName,
        selected = selected
    )
}
