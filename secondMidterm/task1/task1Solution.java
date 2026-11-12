import java.util.*;
import java.util.stream.Collectors;


//IMAT GRESKA VO PRINTOT!!

//class CloudObject {
//    String prefix;
//    String file;
//
//    //ADD test/test2/test3/stefan.txt
//    public CloudObject(String key) {
//        int lastSlash = key.lastIndexOf("/");
//        if (lastSlash == -1) {
//            this.prefix = "";
//            this.file = key;
//        } else {
//            this.prefix = key.substring(0, lastSlash);
//            this.file = key.substring(lastSlash + 1);
//        }
//    }
//
//}

class Bucket {
    Map<String, List<String>> cloudObjects;
    String name;

    public Bucket(String name) {
        cloudObjects = new HashMap<>();
        this.name = name;
    }

    public void addObject(String key) {
        int lashSlash = key.lastIndexOf("/");
        String prefix;
        String file;
        //indekso na poslednio slash
        if (lashSlash == -1) {
            prefix = "";
            file = key;
        } else {
            prefix = key.substring(0, lashSlash);
            file = key.substring(lashSlash + 1);
        }
        cloudObjects.putIfAbsent(prefix, new ArrayList<>());
        cloudObjects.get(prefix).add(file);
    }

    public void removeObject(String key) {
        //REMOVE test/test2/test3/stefan.txt
        //При бришење на постоечки објект, да се направи чистење
        //и на префиксите за тој објект
        //(односно да не се оставаат префикси во кофата
        //без датотеки во нив)
        int lashSlash = key.lastIndexOf("/");
        String prefix;
        String file;

        if (lashSlash == -1) {
            prefix = "";
            file = key;
        } else {
            prefix = key.substring(0, lashSlash);
            file = key.substring(lashSlash + 1);
        }

        if (cloudObjects.containsKey(prefix)) {
            List<String> values = cloudObjects.get(prefix);
            values.remove(file);

            if (values.isEmpty()) {
                cloudObjects.remove(prefix);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(name).append("/\n");

        List<String> fullPaths = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : cloudObjects.entrySet()) {
            String prefix = entry.getKey();

            for (String file : entry.getValue()) {
                if (prefix.equals("")) {
                    fullPaths.add(file);
                } else {
                    fullPaths.add(prefix + "/" + file);
                }
            }
        }

        Set<String> printed = new HashSet<>();

        for (String path : fullPaths) {
            String[] parts = path.split("/");

            String currentPath = "";

            for (int i = 0; i < parts.length; i++) {
                if (currentPath.equals("")) {
                    currentPath = parts[i];
                } else {
                    currentPath += "/" + parts[i];
                }

                if (!printed.contains(currentPath)) {
                    for (int j = 0; j <= i; j++) {
                        sb.append("\t"); // tab namesto 4 spaces
                    }

                    sb.append(parts[i]);

                    if (i != parts.length - 1) {
                        sb.append("/");
                    }

                    sb.append("\n");
                    printed.add(currentPath);
                }
            }
        }

        return sb.toString();
    }
}


public class BucketTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // bucket name is fixed
        Bucket bucket = new Bucket("bucket");

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+", 2);
            String command = parts[0];

            if (command.equalsIgnoreCase("ADD")) {
                bucket.addObject(parts[1]);
            } else if (command.equalsIgnoreCase("REMOVE")) {
                bucket.removeObject(parts[1]);
            } else if (command.equalsIgnoreCase("PRINT")) {
                System.out.print(bucket);
            }
        }
    }
}


