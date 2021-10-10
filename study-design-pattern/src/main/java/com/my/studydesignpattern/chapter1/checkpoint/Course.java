package com.my.studydesignpattern.chapter1.checkpoint;

import java.util.List;
import java.util.stream.Collectors;

/*
chapter1 checkpoint 31p
 */

public class Course {

    private List<Transcript> transcripts;

    public List<Student> getStudents() {
        return transcripts.stream()
            .map(Transcript::getStudent)
            .collect(Collectors.toList());
    }

}
