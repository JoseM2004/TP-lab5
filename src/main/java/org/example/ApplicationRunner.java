package org.example;

import java.text.MessageFormat;
import java.time.LocalDate;

import org.example.exception.GradeNotFoundException;
import org.example.model.Grade;
import org.example.repository.GradeUsingFileRepositoryImpl;
import org.example.service.AcademicRecordService;
import org.example.service.AcademicRecordServiceImpl;


public class ApplicationRunner {
  public static void main(String[] args) {

    AcademicRecordService academicRecordService =
        new AcademicRecordServiceImpl(new GradeUsingFileRepositoryImpl());

    printGrades(academicRecordService);

    academicRecordService.addGrade(createNewGrade("PARCIAL", 4.5D, LocalDate.now()));

    printGrades(academicRecordService);

    System.out.println(
            MessageFormat.format(
                    "\nSuma de número calificaciones: {0}", academicRecordService.sumNumberOfGrades()));

    System.out.println(
            MessageFormat.format("\nPromedio: {0}", academicRecordService.calculateAverage()));

    consultGrade("Unidad 10", academicRecordService);

  }

  static void printGrades(AcademicRecordService academicRecordService)
  {
    if (academicRecordService.listAllGrades().size() == 4)
    {
      System.out.println("Notas iniciales");
      academicRecordService.listAllGrades().forEach(System.out::println);
    } else if (academicRecordService.listAllGrades().size() > 4) {
      System.out.println("\nNotas despues de adicionar una nueva");
      academicRecordService.listAllGrades().forEach(System.out::println);
    }
  }
  static Grade createNewGrade(String project, Double grade, LocalDate submissionDate)
  {
    return new Grade(project, grade, submissionDate);
  }

  static void consultGrade(String nombreProyecto,AcademicRecordService academicRecordService)
  {
    try {
      System.out.println(academicRecordService.getGrade(nombreProyecto));
    }
    catch (GradeNotFoundException e) {
      System.out.println(MessageFormat.format("\nNo se encontró una nota para la unidad {0} ", nombreProyecto));
    }
  }
}
