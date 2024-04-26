package org.tinkoffEducation2024;

import java.util.*;

public class Task3 {

    static class Directory implements Comparable<Directory> {
        String name;
        TreeSet<Directory> subfolder;

        public Directory(String name) {
            this.name = name;
            this.subfolder = new TreeSet<>();
        }

        @Override
        public int compareTo(Directory other) {
            return this.name.compareTo(other.name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Directory directory)) return false;
            return Objects.equals(name, directory.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    static Directory getTree(List<String> paths) {
        Directory root = new Directory("");

        for (String path : paths) {
            String[] dirs = path.split("/");
            Directory current = root;

            for (String dir : dirs) {
                if (!dir.isEmpty()) {
                    Directory child = null;
                    for (Directory d : current.subfolder) {
                        if (d.name.equals(dir)) {
                            child = d;
                            break;
                        }
                    }
                    if (child == null) {
                        child = new Directory(dir);
                        current.subfolder.add(child);
                    }
                    current = child;
                }
            }
        }
        return root;
    }

    static void printDir(Directory dir, int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ".repeat(Math.max(0, indent - 1)));
        //пропуск корня
        if (!dir.name.isEmpty()) {
            sb.append(dir.name);
            System.out.println(sb);
        }
        //рекурсивно обход дерева
        for (Directory child : dir.subfolder) {
            printDir(child, indent + 1);
        }
    }

    public static void main(String[] args) {
        String strTemp = "6\n" +
                "root/a\n" +
                "root/a/b\n" +
                "root/c/x\n" +
                "root/a/b/c\n" +
                "root\n" +
                "root/c\n";
        String strTemp1 = "6\n" +
                "root/n\n" +
                "root/a/b\n" +
                "root/a/x\n" +
                "root/n/c\n" +
                "root\n" +
                "root/c\n";
        String strTemp2 = "4\n" +
                "a/b/c/d\n" +
                "a/b\n" +
                "a/b/c\n" +
                "a\n";
        Scanner scanner = new Scanner(strTemp1);
        int n = scanner.nextInt();
        scanner.nextLine();
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            paths.add(scanner.nextLine());
        }
        Directory root = getTree(paths);
        printDir(root, 0);
    }
}

