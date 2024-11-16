package ua.everybuy.routing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.everybuy.routing.dto.response.subresponse.SubResponseMarker;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class StatusResponse {
    private int status;
    private SubResponseMarker data;
}
 class IPAddresses {
    public static long countIPBetween(String start, String end) {
        String [] starts = start.split("\\.");
        String [] ends = end.split("\\.");
        long startBytes=0;
        long endBytes=0;
        int power = 3;
        System.out.println("first numbers");
        for (String first:starts) {
            System.out.println(first);
            startBytes+=Math.pow(256, power)*Integer.parseInt(first);
            power--;
        }
        int powerSecond = 3;
        System.out.println("second numbers");
        for (String second:ends) {
            System.out.println(second);
            endBytes+=Math.pow(256, powerSecond)*Integer.parseInt(second);
            powerSecond--;
        }
        System.out.println("endBytes = " + endBytes);
        System.out.println("startBytes = " + startBytes);
        return endBytes-startBytes;
    }

     public static void main(String[] args) {
         System.out.println("res =" + countIPBetween("150.0.0.0", "150.0.0.1"));
     }

}
