package cookcook.nexters.com.amoogye.utils

import cookcook.nexters.com.amoogye.views.tools.*
import java.util.*

// 단위는 ML로 계산한다.
val realmData = arrayOf(

    MeasureUnit(1, TYPE_LIFE, ITEM_STATUS_ON,"밥숟가락","","",10.0,false,Date().time, Date().time),
    MeasureUnit(2, TYPE_LIFE,ITEM_STATUS_ON,"베라스푼","","",5.0,false,Date().time, Date().time),
    MeasureUnit(3, TYPE_LIFE,ITEM_STATUS_ON,"종이컵","","",180.0,false,Date().time, Date().time),
    MeasureUnit(4, TYPE_LIFE,ITEM_STATUS_ON,"소주잔","","",50.0,false,Date().time, Date().time),
    MeasureUnit(5, TYPE_LIFE,ITEM_STATUS_ON,"병뚜껑", "","",5.0,false,Date().time, Date().time),
    MeasureUnit(6, TYPE_LIFE, ITEM_STATUS_ON,"물뚜껑","","",5.0,false,Date().time, Date().time),
    MeasureUnit(7, TYPE_LIFE,0,"작은김용기","","",70.0,false,Date().time, Date().time),
    MeasureUnit(8, TYPE_LIFE,0,"큰김용기","","",100.0,false,Date().time, Date().time),
    MeasureUnit(9, TYPE_LIFE,0,"참치캔","","",200.0,false,Date().time, Date().time),
    MeasureUnit(10, TYPE_LIFE,0,"햇반그릇","","",210.0,false,Date().time, Date().time),

    MeasureUnit(11, TYPE_NORMAL, ITEM_STATUS_ON, "g", "그램", "", 1.0, true, Date().time, Date().time),
    MeasureUnit(12, TYPE_NORMAL, ITEM_STATUS_ON, "kg", "킬로그램", "", 1000.0, true, Date().time, Date().time),
    MeasureUnit(13, TYPE_NORMAL,ITEM_STATUS_ON,"cc","시시","",1.0,false,Date().time, Date().time),
    MeasureUnit(14, TYPE_NORMAL,ITEM_STATUS_ON,"L","리터","",1000.0,false,Date().time, Date().time),
    MeasureUnit(15, TYPE_NORMAL,ITEM_STATUS_ON,"ml", "밀리리터","",1.0,false,Date().time, Date().time),
    MeasureUnit(16, TYPE_NORMAL, ITEM_STATUS_ON,"큰술","tbsp","",15.0,false,Date().time, Date().time),
    MeasureUnit(17, TYPE_NORMAL,ITEM_STATUS_ON,"작은술","tsp","",5.0,false,Date().time, Date().time),
    MeasureUnit(18, TYPE_NORMAL, 0, "lb", "파운드","",453.59237,true,Date().time, Date().time),
    MeasureUnit(19, TYPE_NORMAL,0,"oz", "온즈","",29.57353,false,Date().time, Date().time),
    MeasureUnit(20, TYPE_NORMAL,0,"컵","cup","",200.0,false,Date().time, Date().time),
    MeasureUnit(21, TYPE_NORMAL,0,"us컵","cup","",240.0,false,Date().time, Date().time),


    MeasureUnit(22, TYPE_FOOD,0,"백설탕","","",1.17645,false,Date().time, Date().time),
    MeasureUnit(23, TYPE_FOOD,0,"물","","",1.0,false,Date().time, Date().time),
    MeasureUnit(24, TYPE_FOOD,0,"밀가루","","",2.38095,false,Date().time, Date().time),
    MeasureUnit(25, TYPE_FOOD,0,"강려분","","",1.49255,false,Date().time, Date().time),
    MeasureUnit(26, TYPE_FOOD,0,"중력분","","",1.8182, false,Date().time, Date().time),
    MeasureUnit(27, TYPE_FOOD,0,"박력분","","",2.0,false,Date().time, Date().time),
    MeasureUnit(28, TYPE_FOOD,0,"이스트","","",1.56475,false,Date().time, Date().time),
    MeasureUnit(29, TYPE_FOOD,0,"버터","","",1.0432,false,Date().time, Date().time),
    MeasureUnit(30, TYPE_FOOD,0,"베이킹파우더","","",1.0715,false,Date().time, Date().time),
    MeasureUnit(31, TYPE_FOOD,0,"슈가파우더","","",1.8927,false,Date().time, Date().time),
    MeasureUnit(32, TYPE_FOOD,0,"소금","","",0.9524,false,Date().time, Date().time),
    MeasureUnit(33, TYPE_FOOD,0,"물엿","","",0.7143,false,Date().time, Date().time),
    MeasureUnit(34, TYPE_FOOD,0,"카놀라유","","",1.07525,false,Date().time, Date().time),
    MeasureUnit(35, TYPE_FOOD,0,"꿀","","",0.7213,false,Date().time, Date().time),
    MeasureUnit(36, TYPE_FOOD,0,"전지분유","","",3.47925,false,Date().time, Date().time),
    MeasureUnit(37, TYPE_FOOD,0,"탈지분유","","",1.84845,false,Date().time, Date().time),
    MeasureUnit(38, TYPE_FOOD,0,"아몬드가루","","",2.46445, false,Date().time, Date().time),
    MeasureUnit(39, TYPE_FOOD,0,"밀크초콜릿","","",1.40825,false,Date().time, Date().time),
    MeasureUnit(40, TYPE_FOOD,0,"계란","","",0.9736,false,Date().time, Date().time),
    MeasureUnit(41, TYPE_FOOD,0,"귀리","","",2.62875,false,Date().time, Date().time),
    MeasureUnit(42, TYPE_FOOD,0,"현미","","",1.2452,false,Date().time, Date().time),
    MeasureUnit(43, TYPE_FOOD,0,"쌀가루","","",1.4787,false,Date().time, Date().time),
    MeasureUnit(44, TYPE_FOOD,0,"연유","","",0.77315,false,Date().time, Date().time),
    MeasureUnit(45, TYPE_FOOD,0,"메이플시럽","","",0.74715,false,Date().time, Date().time),
    MeasureUnit(46, TYPE_FOOD,0,"다진마늘","","",0.89285714,false,Date().time, Date().time),
    MeasureUnit(47, TYPE_FOOD,0,"간장","","",0.8333,false,Date().time, Date().time),
    MeasureUnit(48, TYPE_FOOD,0,"깨소금","","",2.5,false,Date().time, Date().time),
    MeasureUnit(49, TYPE_FOOD,0,"참기름","","",1.25,false,Date().time, Date().time),
    MeasureUnit(50, TYPE_FOOD,0,"된장","","",1.5,false,Date().time, Date().time),
    MeasureUnit(51, TYPE_FOOD,0,"청주","","",1.0,false,Date().time, Date().time),
    MeasureUnit(52, TYPE_FOOD,0,"고춧가루","","",1.66667,false,Date().time, Date().time),
    MeasureUnit(53, TYPE_FOOD,0,"고추장","","",1.0,false,Date().time, Date().time),
    MeasureUnit(54, TYPE_FOOD,0,"치킨파우더","","",1.5,false,Date().time, Date().time),
    MeasureUnit(55, TYPE_FOOD,0,"굴소스","","",1.5,false,Date().time, Date().time),
    MeasureUnit(56, TYPE_FOOD,0,"겨자가루","","",1.5,false,Date().time, Date().time),
    MeasureUnit(57, TYPE_FOOD,0,"녹말가루","","",1.5,false,Date().time, Date().time),
    MeasureUnit(58, TYPE_FOOD,0,"통조림옥수수","","",1.0,false,Date().time, Date().time),
    MeasureUnit(59, TYPE_FOOD,0,"피자치즈","","",2.0,false,Date().time, Date().time),
    MeasureUnit(60, TYPE_FOOD,0,"완두콩","","",1.5,false,Date().time, Date().time),
    MeasureUnit(61, TYPE_FOOD,0,"마요네즈","","",1.5,false,Date().time, Date().time)

)