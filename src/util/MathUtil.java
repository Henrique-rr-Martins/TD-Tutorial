package util;

import static java.lang.Math.*;

public class MathUtil {

    public static int getHypoDistance(float x1, float y1, float x2, float y2){
        float xDif = abs(x1-x2);
        float yDif = abs(y1-y2);

        return (int) abs(hypot(xDif, yDif));
    }
}
