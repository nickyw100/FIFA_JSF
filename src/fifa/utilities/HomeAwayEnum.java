package fifa.utilities;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum HomeAwayEnum {
    Home("H"), Away("A");

    private static final Map<String, HomeAwayEnum> valueMap;

    static {
        valueMap = new HashMap();


        for (HomeAwayEnum instance : EnumSet.allOf(HomeAwayEnum.class)) {
            valueMap.put(instance.getValue(), instance);
        }
    }

    private String value;


    HomeAwayEnum(String value) {
        this.value = value;
    }


    public static HomeAwayEnum findByValue(String value) {
        HomeAwayEnum type = valueMap.get(value);
        if (type != null) {
            return type;
        }
        return Home;
    }


    public String getValue() {
        return this.value;
    }
}

