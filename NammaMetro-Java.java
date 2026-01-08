import java.util.*;

public class MetroApp {
    // We store the metro map in a HashMap
    static Map<String, List<String>> network = new HashMap<>();

    static void addStation(String name, String... neighbors) {
        network.put(name, Arrays.asList(neighbors));
    }

    public static void main(String[] args) {
        // Build the Bangalore Metro Map
        addStation("Whitefield", "Indiranagar");
        addStation("Indiranagar", "Whitefield", "MG Road");
        addStation("MG Road", "Indiranagar", "Majestic");
        addStation("Majestic", "MG Road", "Mantri Square", "Jayanagar"); // INTERCHANGE
        addStation("Mantri Square", "Majestic", "Nagasandra");
        addStation("Nagasandra", "Mantri Square");
        addStation("Jayanagar", "Majestic", "Silk Institute");
        addStation("Silk Institute", "Jayanagar");

        Scanner sc = new Scanner(System.in);
        System.out.println("NAMMA METRO NAVIGATOR (BETA)");
        System.out.print("Start Station: ");
        String start = sc.nextLine();
        System.out.print("End Station: ");
        String end = sc.nextLine();

        List<String> route = findRoute(start, end);
        
        if (route != null) {
            System.out.println("\n✅ Route Found: " + String.join(" -> ", route));
            int fare = calculateFare(route.size());
            System.out.println("💰 Estimated Fare: ₹" + fare);
        } else {
            System.out.println("❌ No route found. Check station names.");
        }
    }

    // BFS Algorithm to find the shortest path (Recruiters love this!)
    public static List<String> findRoute(String start, String end) {
        Queue<List<String>> queue = new LinkedList<>();
        queue.add(Collections.singletonList(start));
        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String station = path.get(path.size() - 1);

            if (station.equals(end)) return path;

            if (!visited.contains(station) && network.containsKey(station)) {
                visited.add(station);
                for (String neighbor : network.get(station)) {
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }
        return null;
    }

    public static int calculateFare(int stationCount) {
        if (stationCount <= 2) return 10;
        return 10 + (stationCount - 2) * 5; // ₹5 for every extra station
    }
}