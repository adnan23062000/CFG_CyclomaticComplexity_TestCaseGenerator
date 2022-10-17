package cfg;
import java.util.ArrayList;

public class calculateComplexity {

    SyntaxIdentifier checker = new SyntaxIdentifier();
    int cur;
    public String calculate(ArrayList<String> Lines) {

        return  calc_complexity(Lines);
    }

    private String calc_complexity(ArrayList<String> Lines){
        int complexity = 0, ifs =0, elsifs = 0, els = 0, loops = 0;
        for(int i=0; i<Lines.size(); i++){
            if(checker.isElse(Lines.get(i))){
                els++;
            }
            else if(checker.isElseIf(Lines.get(i))){
                elsifs++;
            }
            else if(checker.isIf(Lines.get(i))){
                ifs++;
            }
            else if(checker.isLoop(Lines.get(i))){
                loops++;
            }
            else {
            }
        }


        return "ifs: " + ifs + "\nelse if and else: " + elsifs+els + "\nloops: " + loops;
    }
}