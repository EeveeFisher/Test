import java.util.*;

class Student {
    private String name;
    private int score;
    private int schoolId;

    public Student(String name, int score, int schoolId) {
        this.name = name;
        this.score = score;
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getSchoolId() {
        return schoolId;
    }

    @Override
    public String toString() {
        return name + " (School ID: " + schoolId + ") - Score: " + score;
    }
}

public class SchoolScoreMerger {

    static class StudentEntry {
        Student student;
        int indexInList;     
        List<Student> schoolList;

        public StudentEntry(Student student, int indexInList, List<Student> schoolList) {
            this.student = student;
            this.indexInList = indexInList;
            this.schoolList = schoolList;
        }
    }

    public List<Student> mergeScores(Map<Integer, List<Student>> schoolsStudentScores) {
        if (schoolsStudentScores == null || schoolsStudentScores.isEmpty()) {
            throw new IllegalArgumentException("全部学校的学生分数不能为空");
        }

        for(Integer schoolId : schoolsStudentScores.keySet()) {
            List<Student> students = schoolsStudentScores.get(schoolId);
            if (students == null || students.isEmpty()) {
                throw new IllegalArgumentException(String.format("学校%d的学生成绩列表是空", schoolId));
            }
            students.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        }

        PriorityQueue<StudentEntry> maxHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(b.student.getScore(), a.student.getScore())
        );

        for (List<Student> students : schoolsStudentScores.values()) {
            if (students == null || students.isEmpty()) {
                continue;
            }
            maxHeap.offer(new StudentEntry(students.get(0), 0, students));
        }

        List<Student> mergedList = new ArrayList<>();

        while (!maxHeap.isEmpty()) {
            StudentEntry currentEntry = maxHeap.poll();
            if (currentEntry.student.getScore() < 0) {
                throw new IllegalArgumentException(String.format("学校%d的学生%s的成绩是负数", currentEntry.student.getSchoolId(), currentEntry.student.getName()));
            }
            mergedList.add(currentEntry.student);

            int nextIndex = currentEntry.indexInList + 1;
            if (nextIndex < currentEntry.schoolList.size()) {
                maxHeap.offer(new StudentEntry(currentEntry.schoolList.get(nextIndex), nextIndex, currentEntry.schoolList));
            }
        }

        return mergedList;
    }   

    public static void main(String[] args) {
        SchoolScoreMerger merger = new SchoolScoreMerger();
        
        // 测试用例 1
        System.out.println("测试用例 1:");
        try {
            Map<Integer, List<Student>> case1 = new HashMap<>();
            case1.put(1, Arrays.asList(new Student("小明", 85, 1), new Student("小赵", 88, 1)));
            case1.put(2, Arrays.asList(new Student("小张", 88, 2), new Student("小李", 88, 2)));
            case1.put(3, Arrays.asList(new Student("小刘", 95, 3), new Student("小汤", 85, 3)));
            
            List<Student> result1 = merger.mergeScores(case1);
            result1.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }

        // 测试用例 2
        System.out.println("\n测试用例 2:");
        try {
            Map<Integer, List<Student>> case2 = new HashMap<>();
            case2.put(1, Arrays.asList(new Student("小明", 85, 1), new Student("小赵", 78, 1)));
            
            List<Student> result2 = merger.mergeScores(case2);
            result2.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }

        // 测试用例 3
        System.out.println("\n测试用例 3:");
        try {
            Map<Integer, List<Student>> case3 = new HashMap<>();
            case3.put(1, Arrays.asList(new Student("小明", 85, 1), new Student("小赵", 78, 1)));
            case3.put(2, new ArrayList<>());
            
            List<Student> result3 = merger.mergeScores(case3);
            result3.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }

        // 测试用例 4
        System.out.println("\n测试用例 4:");
        try {
            Map<Integer, List<Student>> case4 = new HashMap<>();
            case4.put(1, Arrays.asList(new Student("小明", 0, 1), new Student("小赵", 78, 1)));
            
            List<Student> result4 = merger.mergeScores(case4);
            result4.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }

        // 测试用例 5
        System.out.println("\n测试用例 5:");
        try {
            Map<Integer, List<Student>> case5 = new HashMap<>();
            case5.put(1, Arrays.asList(new Student("小明", -100, 1), new Student("小赵", 78, 1)));
            
            List<Student> result5 = merger.mergeScores(case5);
            result5.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }

        // 测试用例 6
        System.out.println("\n测试用例 6:");
        try {
            Map<Integer, List<Student>> case6 = new HashMap<>();
            List<Student> result6 = merger.mergeScores(case6);
            result6.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
    }
}