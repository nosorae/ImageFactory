package com.yessorae.data.local.database.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import com.yessorae.common.Constants
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestBody
import com.yessorae.data.remote.stablediffusion.model.response.FetchQueuedImageDto
import com.yessorae.data.remote.stablediffusion.model.response.MetaDataDto
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgDto
import com.yessorae.data.util.DBConstants
import java.time.LocalDateTime

@Entity(tableName = DBConstants.TABLE_TXT_TO_IMG_HISTORY)
data class TxtToImgHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DBConstants.COL_ID)
    var id: Int = 0,
    @ColumnInfo(DBConstants.COL_CREATED_AT)
    val createdAt: LocalDateTime,
    @Embedded(prefix = DBConstants.PREFIX_REQUEST)
    val request: RequestBodyEntity,
    @Embedded(prefix = DBConstants.PREFIX_META)
    val meta: ResultMetaDataEntity? = null,
    @Embedded(prefix = DBConstants.PREFIX_RESULT)
    val result: ResultEntity? = null,
    @ColumnInfo(name = DBConstants.COL_SERVER_SYNC)
    val serverSync: Boolean = false
)

data class RequestBodyEntity(
    @ColumnInfo(name = "model_id")
    val modelId: String,
    @ColumnInfo(name = "prompt")
    val prompt: String,
    @ColumnInfo(name = "negative_prompt")
    val negativePrompt: String,
    @ColumnInfo(name = "width")
    val width: Int,
    @ColumnInfo(name = "height")
    val height: Int,
    @ColumnInfo(name = "samples")
    val samples: Int,
    @ColumnInfo(name = "num_inference_steps")
    val numInferenceSteps: Int,
    @ColumnInfo(name = "safety_checker")
    val safetyChecker: String,
    @ColumnInfo(name = "enhance_prompt")
    val enhancePrompt: String,
    @ColumnInfo(name = "seed")
    val seed: String?,
    @ColumnInfo(name = "guidance_scale")
    val guidanceScale: Double,
    @ColumnInfo(name = "lora_strength")
    val loraStrength: String?,
    @ColumnInfo(name = "lora_model")
    val loraModel: String?,
    @ColumnInfo(name = "multi_lingual")
    val multiLingual: String,
    @ColumnInfo(name = "upscale")
    val upscale: String,
    @ColumnInfo(name = "embeddings_model")
    val embeddingsModel: String?,
    @ColumnInfo(name = "scheduler")
    val scheduler: String,
    @ColumnInfo(name = "clip_skip")
    val clipSkip: Int = 2,
    @ColumnInfo(name = "safety_checker_type")
    val safetyCheckerType: String = "blur",
    @ColumnInfo(name = "tomesd")
    val tomesd: String = Constants.ARG_YES,
    @ColumnInfo(name = "use_karras_sigmas")
    val useKarrasSigmas: String = Constants.ARG_YES,
    @ColumnInfo(name = "algorithm_type")
    val algorithmType: String = "sde-dpmsolver++",
    @ColumnInfo(name = "vae")
    val vae: String? = null,
    @ColumnInfo(name = "panorama")
    val panorama: String = Constants.ARG_NO,
    @ColumnInfo(name = "self_attention")
    val selfAttention: String = Constants.ARG_YES,
    @ColumnInfo(name = "base64")
    val base64: String = Constants.ARG_NO,
    @ColumnInfo(name = "webhook")
    val webhook: String? = null,
    @ColumnInfo(name = "track_id")
    val trackId: String? = null,
    @ColumnInfo(name = "temp")
    val temp: String = Constants.ARG_YES
)

data class ResultEntity(
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "generation_time")
    val generationTime: Double,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "output")
    val output: List<String>
)

data class ResultMetaDataEntity(
    @ColumnInfo(name = "prompt")
    val prompt: String,
    @ColumnInfo(name = "model_id")
    val modelId: String,
    @ColumnInfo(name = "negative_prompt")
    val negativePrompt: String,
    @ColumnInfo(name = "W")
    val w: Int,
    @ColumnInfo(name = "H")
    val h: Int,
    @ColumnInfo(name = "guidance_scale")
    val guidanceScale: Double,
    @ColumnInfo(name = "seed")
    val seed: Long?,
    @ColumnInfo(name = "steps")
    val steps: Int,
    @ColumnInfo(name = "n_samples")
    val nSamples: Int,
    @ColumnInfo(name = "full_url")
    val fullUrl: String,
    @ColumnInfo(name = "upscale")
    val upscale: String,
    @ColumnInfo(name = "multi_lingual")
    val multiLingual: String,
    @ColumnInfo(name = "panorama")
    val panorama: String,
    @ColumnInfo(name = "self_attention")
    val selfAttention: String,
    @ColumnInfo(name = "embeddings")
    val embeddings: String?,
    @ColumnInfo(name = "lora")
    val lora: String?,
    @ColumnInfo(name = "outdir")
    val outdir: String?,
    @ColumnInfo(name = "file_prefix")
    val filePrefix: String?
)

fun TxtToImgRequestBody.asHistoryEntity(): TxtToImgHistoryEntity {
    return TxtToImgHistoryEntity(
        createdAt = LocalDateTime.now(),
        request = this.asEntity()
    )
}

fun TxtToImgRequestBody.asEntity(): RequestBodyEntity {
    return RequestBodyEntity(
        modelId = this.modelId,
        prompt = this.prompt,
        negativePrompt = this.negativePrompt,
        width = this.width,
        height = this.height,
        samples = this.samples,
        numInferenceSteps = this.numInferenceSteps,
        safetyChecker = this.safetyChecker,
        enhancePrompt = this.enhancePrompt,
        seed = this.seed,
        guidanceScale = this.guidanceScale,
        loraStrength = this.loraStrength,
        loraModel = this.loraModel,
        multiLingual = this.multiLingual,
        upscale = this.upscale,
        embeddingsModel = this.embeddingsModel,
        scheduler = this.scheduler
    )
}

fun RequestBodyEntity.asRequestBody(): TxtToImgRequestBody {
    return TxtToImgRequestBody(
        modelId = this.modelId,
        prompt = this.prompt,
        negativePrompt = this.negativePrompt,
        width = this.width,
        height = this.height,
        samples = this.samples,
        numInferenceSteps = this.numInferenceSteps,
        safetyChecker = this.safetyChecker,
        enhancePrompt = this.enhancePrompt,
        seed = this.seed,
        guidanceScale = this.guidanceScale,
        loraStrength = this.loraStrength,
        loraModel = this.loraModel,
        multiLingual = this.multiLingual,
        upscale = this.upscale,
        embeddingsModel = this.embeddingsModel,
        scheduler = this.scheduler
    )
}

fun TxtToImgDto.asResultEntity(): ResultEntity {
    return ResultEntity(
        status = this.status,
        generationTime = this.generationTime,
        id = this.id,
        output = this.output
    )
}

fun ResultEntity.asDto(metaDataDto: ResultMetaDataEntity): TxtToImgDto {
    return TxtToImgDto(
        status = this.status,
        generationTime = this.generationTime,
        id = this.id,
        output = this.output,
        meta = metaDataDto.asDto()
    )
}

fun ResultMetaDataEntity.asDto(): MetaDataDto {
    return MetaDataDto(
        prompt = this.prompt,
        modelId = this.modelId,
        negativePrompt = this.negativePrompt,
        w = this.w,
        h = this.h,
        guidanceScale = this.guidanceScale,
        seed = this.seed,
        steps = this.steps,
        nSamples = this.nSamples,
        fullUrl = this.fullUrl,
        upscale = this.upscale,
        multiLingual = this.multiLingual,
        panorama = this.panorama,
        selfAttention = this.selfAttention,
        embeddings = this.embeddings,
        lora = this.lora,
        outdir = this.outdir,
        filePrefix = this.filePrefix,
    )
}

fun MetaDataDto.asEntity(): ResultMetaDataEntity {
    return ResultMetaDataEntity(
        prompt = this.prompt,
        modelId = this.modelId,
        negativePrompt = this.negativePrompt,
        w = this.w,
        h = this.h,
        guidanceScale = this.guidanceScale,
        seed = this.seed,
        steps = this.steps,
        nSamples = this.nSamples,
        fullUrl = this.fullUrl,
        upscale = this.upscale,
        multiLingual = this.multiLingual,
        panorama = this.panorama,
        selfAttention = this.selfAttention,
        embeddings = this.embeddings,
        lora = this.lora,
        outdir = this.outdir,
        filePrefix = this.filePrefix,
    )
}
