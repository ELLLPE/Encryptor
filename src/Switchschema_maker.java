
public class Switchschema_maker {

    public static void main(String[] args) {
        // För att komma fram till motsattsen till nummret

        int alphaLength = 70;
        int alphaDevided = (alphaLength / 2);

        int[] deflector = new int[alphaLength];
        for (int i = 0, o = alphaDevided; i < (alphaDevided) && o < alphaLength; i++, o++) {

            deflector[i] = o;
            deflector[o] = i;
        }

        for (int i = 0; i < (alphaLength - 1); i++) {
            System.out.print(deflector[i] + ", ");
        }
        System.out.println(deflector[(alphaLength - 1)]);

        int[] rand = new int[alphaDevided];
        int ii = 0;
        while (ii < alphaDevided) {

            rand[ii] = (int) (Math.random() * alphaDevided) + alphaDevided;

            if (ii != 0) {
                int failTest = 0;

                while (failTest < (ii + 1)) {

                    if (rand[failTest] == failTest) {

                        ii--;
                        failTest = (ii + 1);
                    }
                    if (rand[ii] == rand[failTest] && failTest != ii) {

                        ii--;
                        failTest = (ii + 1);
                    }
                    failTest++;
                }

            }
            ii++;
        }
        int[] unRand = new int[alphaLength];

        System.out.println("_____________________________________________");
        for (int i = 0; i < alphaDevided; i++) {
            System.out.println("PrintSt = " + i);
            System.out.println(i + " --> " + rand[i]);
            System.out.println(rand[i] + " --> " + i);
            unRand[i] = rand[i];
            unRand[rand[i]] = i;
            System.out.println("_____________________________________________");
        }

        for (int i = 0; i < (unRand.length - 1); i++) {
            System.out.print(unRand[i] + ", ");
        }
        System.out.println(unRand[unRand.length - 1]);
        System.out.println("slut");
        System.out.println("_____________________________________________");

        int test = 70 / 6;
        System.out.println("test = " + test); // resultat: java kommer avrunda ner oavsätt vad
    }
}
