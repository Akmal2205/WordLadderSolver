import java.util.*;

import Utils.*;

public class Astar extends BaseClass {
    public PriorityQueue<Node> simpul_hidup = new PriorityQueue<Node>(
            new AStarComparator(this.start_word, this.end_word));
    public Set<String> visited = new HashSet<String>();
    public WordValidator w = new WordValidator(this.start_word);

    public Astar(String start_word, String end_word) {
        super(start_word.toLowerCase(), end_word.toLowerCase());
        ArrayList<String> start_path = new ArrayList<String>();
        start_path.add(start_word);
        Node temp_start = new Node(start_word, start_path);
        this.simpul_hidup.add(temp_start);
    }

    public ArrayList<String> expandWord(String word) {
        ArrayList<String> exp_word = new ArrayList<String>();
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < 26; j++) {
                StringBuilder new_word = new StringBuilder(word);
                new_word.setCharAt(i, this.alphabet[j]);
                if (!new_word.toString().toLowerCase().equals(word)
                        && w.isWordValid(new_word.toString().toLowerCase())
                        && !this.visited.contains(new_word.toString())) {
                    exp_word.add(new_word.toString().toLowerCase());
                    this.visited.add(new_word.toString());
                }
            }
        }
        return exp_word;
    }

    public String toPath(ArrayList<String> path) {
        String res = "";
        for (int i = 0; i < path.size() - 1; i++) {
            if (!path.get(i).equals(" ")) {
                res += path.get(i).toString() + "->";
            }
        }
        res += path.get(path.size() - 1).toString();
        return res;
    }

    public void prinSimpulHidup() {
        PriorityQueue<Node> temp = new PriorityQueue<>(this.simpul_hidup);
        for (int i = 0; i < this.simpul_hidup.size(); i++) {
            Node temp_node = temp.poll();
            System.out.print(
                    "{" + temp_node.current_path + " -> " + (temp_node.current_path.size()
                            + this.costCounter(temp_node.word)) + " b(n) :" + temp_node.current_path.size()
                            + " f(n) :" + this.costCounter(temp_node.word) + "}");
        }
    }

    public Node AstarPrioQueue() {
        while (!this.simpul_hidup.isEmpty()) {
            Node current_node = this.simpul_hidup.poll();
            String exp_word = current_node.word;
            ArrayList<String> available_words = this.expandWord(exp_word);
            for (int i = 0; i < available_words.size(); i++) {
                ArrayList<String> temp_path = new ArrayList<String>(current_node.current_path);
                temp_path.add(available_words.get(i));
                Node temp = new Node(available_words.get(i), temp_path);
                this.simpul_hidup.add(temp);
                if (available_words.get(i).equals(this.end_word.toLowerCase())) {
                    return temp;
                }
            }
        }
        Node no_res = null;
        return no_res; // dummy return
    }

    public String[] mainAstar() {
        Astar as = new Astar(this.start_word, this.end_word);
        Node res = new Node(null, null);
        String[] result = { "No Path", "Took 0ms to execute", "Visited 0 node(s)" };
        final long startTime = System.currentTimeMillis();
        res = (as.AstarPrioQueue());
        final long endTime = System.currentTimeMillis();
        if (res != null) {
            as.printPath(res.current_path);
            result[0] = as.toPath(res.current_path);
        }
        result[1] = "Took " + (endTime - startTime) + "ms to execute";
        result[2] = "Visited " + as.visited.size() + " node(s)";
        System.out.println("Took " + (endTime - startTime) + "ms to execute");
        System.out.println("Visited " + as.visited.size() + " node(s)");
        return result;
    }

}
