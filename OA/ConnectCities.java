import java.util.*;

public class ConnectCities {
    static class UnionFind {
        int[] parent;
        int[] rank;
        int count; // 剩下多少个独立的集合

        public UnionFind(int n) {
            parent = new int[n + 1]; // 城市编号从1开始
            rank = new int[n + 1];
            count = n;
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // 路径压缩
            }
            return parent[x];
        }

        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) return false;

            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            count--;
            return true;
        }

        public int getCount() {
            return count;
        }
    }

    public int minimumCost(int N, int[][] connections) {
        Arrays.sort(connections, (a, b) -> a[2] - b[2]); // 按权重排序
        UnionFind uf = new UnionFind(N);
        int totalCost = 0;

        for (int[] conn : connections) {
            int city1 = conn[0];
            int city2 = conn[1];
            int cost = conn[2];
            if (uf.union(city1, city2)) {
                totalCost += cost;
            }
        }

        return uf.getCount() == 1 ? totalCost : -1;
    }

    public static void main(String[] args) {
        ConnectCities solver = new ConnectCities();

        int[][] conn1 = {{1, 2, 5}, {1, 3, 6}, {2, 3, 1}};
        System.out.println(solver.minimumCost(3, conn1)); // 输出 6

        int[][] conn2 = {{1, 2, 3}, {3, 4, 4}};
        System.out.println(solver.minimumCost(4, conn2)); // 输出 -1
    }
}
