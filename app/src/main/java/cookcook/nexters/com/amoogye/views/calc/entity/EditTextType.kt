package cookcook.nexters.com.amoogye.views.calc.entity

enum class EditTextType {
    // (K)명 기준 (N) (M)의 (P)를 (T)명 기준 (A)로 바꾸면?
    HUMAN_ONE, // K (ex, 1명)
    AMOUNT, // N (ex, 10)
    UNIT, // M (ex, ml)
    INGREDIENT, // P (ex, 굴소스)
    HUMAN_TWO, // T (2명)
    TOOL, // A 아빠숟가락
    NONE // 아무것도 선택하지 않음
}