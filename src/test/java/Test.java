import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {


    public static void main(String[] args){
        List<Sirius> siriusList = new ArrayList<>();
        siriusList.add(new Sirius(1,"1"));
        siriusList.add(new Sirius(2,"1"));
        siriusList.add(new Sirius(3,"1"));
        siriusList.add(new Sirius(4,"1"));
        siriusList.add(new Sirius(5,"1"));
        siriusList.add(new Sirius(6,"1"));
        siriusList.add(new Sirius(7,"1"));
        siriusList.add(new Sirius(8,"1"));
       List<Sirius> b = siriusList.stream().filter(a->a.getA().equals(3)).collect(Collectors.toList());
       List<Sirius> c = siriusList.stream().map(a->a.getA().equals(2)).collect(Collectors.toList());
        System.out.println(b);
    }
}
