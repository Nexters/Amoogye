package cookcook.nexters.com.amoogye.views.calc.entity

enum class CalcLayoutState {
    NUMBER, // 숫자 입력
    TOOL,  // 도구 입력
    UNIT,  // 단위 입력
    INGREDIENT // 무게 단위 항목 입력
}

enum class CalcTypeState {
    MATERIAL, // 재료 (ex, 10ml를 아빠숟가락으로 바꾸면)
    PERSONNEL, // 인원 (ex, 1명 기준 10ml를 2명 기준으로 바꾸면)
    MATERIAL_PERSONNEL // 재료 + 인원 (ex, 1명 기준 10ml를 2명 기준 아빠숟가락으로 바꾸면)
}

enum class CalcUnitType {
    LIFE, // 생활 (숟가락, 컵 등)
    NORMAL // 일반 (Ml, kg 등)
}