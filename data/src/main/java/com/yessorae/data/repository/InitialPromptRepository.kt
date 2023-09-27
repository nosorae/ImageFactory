package com.yessorae.data.repository

import com.yessorae.data.local.database.dao.PromptDao
import com.yessorae.data.local.database.model.PromptEntity
import com.yessorae.data.local.preference.PreferenceService
import javax.inject.Inject

class InitialPromptRepository @Inject constructor(
    private val promptDao: PromptDao,
    private val preferenceService: PreferenceService
) {

    /**
     * [civitai 프롬프트 통계](https://rentry.org/toptokens#20-most-common-samplers) 에 기반한 프롬프트 셋
     * 긍정 및 부정 각각 1~100위씩 미리 세팅해준다.
     * 주석처리 된 프롬프트는 19금이거나 화질관련 또는 중복이 심하거나 너무 쓸데없어 보이는 경우.
     */
    suspend fun processInitialPromptData() {
        if (preferenceService.getCompleteInitPromptData().not()) {
            val positivePrompts = listOf(
                PromptEntity.createPositive(prompt = "masterpiece"),
                PromptEntity.createPositive(prompt = "best quality"),
                PromptEntity.createPositive(prompt = "1girl"),
                PromptEntity.createPositive(prompt = "solo"),
                PromptEntity.createPositive(prompt = "looking at viewer"),
                PromptEntity.createPositive(prompt = "realistic"),
//                PromptEntity.createPositive(prompt = "8k"),
                PromptEntity.createPositive(prompt = "sharp focus"),
                PromptEntity.createPositive(prompt = "photorealistic"),
                PromptEntity.createPositive(prompt = "long hair"),
                PromptEntity.createPositive(prompt = "smile"),
                PromptEntity.createPositive(prompt = "full body"),
                PromptEntity.createPositive(prompt = "highres"),
                PromptEntity.createPositive(prompt = "highly detailed"),
                PromptEntity.createPositive(prompt = "ultra-detailed"),
                PromptEntity.createPositive(prompt = "intricate"), // 뒤얽힌
                PromptEntity.createPositive(prompt = "illustration"),
                PromptEntity.createPositive(prompt = "standing"),
                PromptEntity.createPositive(prompt = "blush"),
                PromptEntity.createPositive(prompt = "cinematic lighting"),
                PromptEntity.createPositive(prompt = "bangs"),
                PromptEntity.createPositive(prompt = "hdr"),
                PromptEntity.createPositive(prompt = "intricate details"),
                PromptEntity.createPositive(prompt = "depth of field"),
                PromptEntity.createPositive(prompt = "extremely detailed"),
                PromptEntity.createPositive(prompt = "hdr"),
                PromptEntity.createPositive(prompt = "depth of field"),
                PromptEntity.createPositive(prompt = "intricate details"),
                PromptEntity.createPositive(prompt = "extremely detailed"),
                PromptEntity.createPositive(prompt = "absurdres"),
                PromptEntity.createPositive(prompt = "high quality"),
                PromptEntity.createPositive(prompt = "upper body"),
                PromptEntity.createPositive(prompt = "raw photo"),
                PromptEntity.createPositive(prompt = "detailed eyes"),
                PromptEntity.createPositive(prompt = "short hair"),
                PromptEntity.createPositive(prompt = "black hair"),
                PromptEntity.createPositive(prompt = "detailed face"),
                PromptEntity.createPositive(prompt = "outdoors"),
                PromptEntity.createPositive(prompt = "detailed"),
                PromptEntity.createPositive(prompt = "ultra high res"),
                PromptEntity.createPositive(prompt = "trending on artstation"),
                PromptEntity.createPositive(prompt = "portrait"),
                PromptEntity.createPositive(prompt = "jewelry"),
                PromptEntity.createPositive(prompt = "cowboy shot"),
                PromptEntity.createPositive(prompt = "night"),
                PromptEntity.createPositive(prompt = "beautiful face"),
                PromptEntity.createPositive(prompt = "shiny skin"),
                PromptEntity.createPositive(prompt = "1boy"),
                PromptEntity.createPositive(prompt = "soft lighting"),
                PromptEntity.createPositive(prompt = "film grain"),
                PromptEntity.createPositive(prompt = "perfect face"),
                PromptEntity.createPositive(prompt = "blonde hair"),
                PromptEntity.createPositive(prompt = "white hair"),
                PromptEntity.createPositive(prompt = "red eyes"),
                PromptEntity.createPositive(prompt = "sitting"),
                PromptEntity.createPositive(prompt = "hair ornament"),
                PromptEntity.createPositive(prompt = "beautiful"),
                PromptEntity.createPositive(prompt = "beautiful detailed eyes"),
                PromptEntity.createPositive(prompt = "cute"),
                PromptEntity.createPositive(prompt = "earrings"),
                PromptEntity.createPositive(prompt = "dramatic"),
                PromptEntity.createPositive(prompt = "navel"), // 배꼽
                PromptEntity.createPositive(prompt = "open mouth"),
                PromptEntity.createPositive(prompt = "volumetric lighting"),
                PromptEntity.createPositive(prompt = "closed mouth"),
                PromptEntity.createPositive(prompt = "highest quality"),
                PromptEntity.createPositive(prompt = "photo-realistic"),
                PromptEntity.createPositive(prompt = "bokeh"), // 뭉개진 빛들
                PromptEntity.createPositive(prompt = "bare shoulders"),
                PromptEntity.createPositive(prompt = "ultra detailed"),
                PromptEntity.createPositive(prompt = "artstation"),
                PromptEntity.createPositive(prompt = "brown hair"),
                PromptEntity.createPositive(prompt = "dress"),
                PromptEntity.createPositive(prompt = "1 girl"),
                PromptEntity.createPositive(prompt = "huge breasts"),
                PromptEntity.createPositive(prompt = "concept art"),
                PromptEntity.createPositive(prompt = "cinematic"),
                PromptEntity.createPositive(prompt = "green eyes"),
                PromptEntity.createPositive(prompt = "indoors"),
                PromptEntity.createPositive(prompt = "8k uhd"),
                PromptEntity.createPositive(prompt = "gloves"),
                PromptEntity.createPositive(prompt = "official art"),
                PromptEntity.createPositive(prompt = "thighhighs"),
                PromptEntity.createPositive(prompt = "simple background"),
                PromptEntity.createPositive(prompt = "detailed background"),
                PromptEntity.createPositive(prompt = "skirt"),
                PromptEntity.createPositive(prompt = "smiling"),
                PromptEntity.createPositive(prompt = "smooth"),
                PromptEntity.createPositive(prompt = "long_hair"),
                PromptEntity.createPositive(prompt = "very long hair"),
                PromptEntity.createPositive(prompt = "shirt"),
                PromptEntity.createPositive(prompt = "perfect lighting"),
                PromptEntity.createPositive(prompt = "amazing"),
                PromptEntity.createPositive(prompt = "long sleeves"),
                PromptEntity.createPositive(prompt = "dynamic pose"),
                PromptEntity.createPositive(prompt = "fantasy"),
                PromptEntity.createPositive(prompt = "dslr"),
                PromptEntity.createPositive(prompt = "mature female"),
                PromptEntity.createPositive(prompt = "choker"),
                PromptEntity.createPositive(prompt = "digital painting"),
                PromptEntity.createPositive(prompt = "intricate detail"),
                PromptEntity.createPositive(prompt = "modelshoot style"),
                PromptEntity.createPositive(prompt = "collarbone")
//                PromptEntity.createPositive(prompt = "nsfw"),
//                PromptEntity.createPositive(prompt = "breasts"),
//                PromptEntity.createPositive(prompt = "large breasts"),
//                PromptEntity.createPositive(prompt = "absurdres"),
//                PromptEntity.createPositive(prompt = "4k"),
//                PromptEntity.createPositive(prompt = "blue eyes"),
//                PromptEntity.createPositive(prompt = "extremely detailed cg unity 8k wallpaper"),
//                PromptEntity.createPositive(prompt = "cleavage"),
//                PromptEntity.createPositive(prompt = "medium breasts"),
//                PromptEntity.createPositive(prompt = "small breasts"),
//                PromptEntity.createPositive(prompt = "nipples"),
//                PromptEntity.createPositive(prompt = "pussy"),
//                PromptEntity.createPositive(prompt = "sexy"),
//                PromptEntity.createPositive(prompt = "looking_at_viewer"),

            )
            promptDao.insertAll(
                positivePrompts
            )
        }
    }

    private fun checkInitData() {

    }
}