package main;

import configuration.ProjectConfiguration;
import model.Comment;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import proxies.EmailCommentNotificationProxy;
import repositories.DBCommentRepository;
import services.CommentService;

public class Main {
    /*
    Chapter 4: Abstractions ex1
     */
    public static void main(String[] args) {
//        var commentRepository = new DBCommentRepository();
//        var commentNotificationProxy = new EmailCommentNotificationProxy();
//
//        var commentService = new CommentService(commentRepository, commentNotificationProxy);
//
//        var comment = new Comment();
//        comment.setAuthor("Hiroto");
//        comment.setText("Demo comment!");
//
//        commentService.publishComment(comment);

        var context = new AnnotationConfigApplicationContext(ProjectConfiguration.class);

        var comment = new Comment();
        comment.setAuthor("Hiroto");
        comment.setText("Demo comment!");

        var commentService = context.getBean(CommentService.class);
        commentService.publishComment(comment);
    }
}
