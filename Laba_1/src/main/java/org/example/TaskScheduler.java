package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TaskScheduler {
    private static final Logger logger = LogManager.getLogger(TaskScheduler.class);

    private final List<Task> tasks = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        TaskScheduler app = new TaskScheduler();
        app.run();
    }

    public void run(){
        logger.info("Приложение 'Планировщик' запущено пользователем");
        System.out.println("=== Планировщик задач ===");

        boolean isRunning = true;
        while (isRunning){
            printMenu();
            String choice = scanner.nextLine();

            switch (choice){
                case "1" -> addTask();
                case "2" -> showAllTasks();
                case "3" -> filterByPriority();
                case "4" -> deleteTask();
                case "5" -> {
                    logger.info("Завершенеи работы программы");
                    System.out.println("До свидания!");
                    isRunning = false;
                }
                default -> {
                    System.out.println("Ошибка: Выберите пункт от 1 до 5.");
                    logger.warn("Неверный ввод в меню: {}", choice);
                }
            }
        }
    }

    private void printMenu(){
        System.out.println("\nМеню действий:");
        System.out.println("1. Добавить новую задачу");
        System.out.println("2. Список всех задач");
        System.out.println("3. Фильтр по приоритету");
        System.out.println("4. Удалить задачу");
        System.out.println("5. Выход");
        System.out.print("Ваш выбор: ");
    }

    private void addTask(){
        try{
            System.out.print("Название задачи: ");
            String title = scanner.nextLine();
            System.out.print("Дата (дд.мм.гггг): ");
            String date = scanner.nextLine();
            System.out.print("Приоритет (1 - низкий, 5 - высокий): ");
            int priority = Integer.parseInt(scanner.nextLine());

            if (priority < 1 || priority > 5){
                throw new IllegalArgumentException("Приоритет должен быть в диапазоне 1-5");
            }

            tasks.add(new Task(title, date, priority));
            logger.info("Успешно добавлена задача: {}", title);
            System.out.println("Задача добавлена!");
        } catch (NumberFormatException e){
            logger.error("Критическая ошибка при создании задачи: {}", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showAllTasks(){
        renderList(tasks);
    }

    private void filterByPriority(){
        System.out.print("Показать задачи с приоритетом не ниже: ");
        try{
            int minPrio = Integer.parseInt(scanner.nextLine());
            List<Task> filtered = tasks.stream().filter(t -> t.getPriority() >= minPrio).collect(Collectors.toList());

            logger.info("Выполнена фильтрация по приоритету >= {}", minPrio);
            renderList(filtered);
        } catch (Exception e){
            System.out.println("Ошибка ввода данных для фильтра.");
        }
    }

    private void deleteTask(){
        if (tasks.isEmpty()){
            System.out.println("Список пуст, удалять нечего.");
            return;
        }

        showAllTasks();
        System.out.print("Введите номер задачи для удаления: ");
        try{
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            Task removed = tasks.remove(index);
            logger.info("Удалена задача: {} (индекс {})", removed.getTitle(), index);
            System.out.println("Задача удалена.");
        } catch (Exception e){
            logger.error("Ошибка при попытке удаления задачи: {}", e.getMessage());
            System.out.println("Ошибка: Неверный номер задачи.");
        }
    }

    private void renderList(List<Task> listToPrint){
        if (listToPrint.isEmpty()){
            System.out.println("Список пуст.");
        } else{
            for (int i = 0; i < listToPrint.size(); i++){
                System.out.println((i + 1) + ". " + listToPrint.get(i));
            }
        }
    }
}
