package ru.atom.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private static final String HISTORY_FILE_NAME = "messages_history.txt";

    private Queue<String> messages = new ConcurrentLinkedQueue<>();
    private Map<String, String> usersOnline = new ConcurrentHashMap<>();

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name) {
        if (name.length() < 1) {
            return ResponseEntity.badRequest().body("Too short name, sorry :(");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest().body("Too long name, sorry :(");
        }
        if (usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("Already logged in:(");
        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        usersOnline.put(name, name);
        String message = df.format(new Date()) + "|" + "<font color=\"#457708\"><bold>" +
                " ["  + name + "] Присоединился к чату " + "</bold></font>";
        messages.add(message);
        WriteToFile(df.format(new Date()) + "|" + " ["  + name + "] Присоединился к чату ");
        return ResponseEntity.ok().build();
    }

    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> chat() {
        return new ResponseEntity<>(messages.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")),
                HttpStatus.OK);
    }

    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity online() {
        String responseBody = String.join("\n", usersOnline.keySet().stream().sorted().collect(Collectors.toList()));
        return ResponseEntity.ok(responseBody);
    }

    /**
     * curl -X POST -i localhost:8080/chat/logout -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "logout",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity logout(@RequestParam("name") String name) {
        if (usersOnline.containsKey(name)) {
            usersOnline.remove(name);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String message =  df.format(new Date())+"|"+ "<font color=\"#003af9\"><bold>" +
                    " [" +  name + "] Покинул чат" + "</bold></font>";
            messages.add(message);
            WriteToFile(df.format(new Date())+"|"+ " [" +  name + "] Покинул чат");
            return ResponseEntity.ok("User " + "[" + name + "] logged out" );
        }

        return ResponseEntity.badRequest().body("User " + name + " is not exists");
    }


    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        if (usersOnline.containsKey(name)) {
            String message = "<bold><font color=\"#f90000\">" + df.format( new Date()) +
                    "| [" + name + "]" +  ":"+ "</font></bold>" + msg;
            messages.add(message);
            WriteToFile(df.format( new Date()) +
                    "| [" + name + "]" +  ":"+ msg);
            return ResponseEntity.ok("(" + name  + "): " + msg);
        } else {
            return ResponseEntity.badRequest().body("User with name " + name + " is not found");
        }
    }


    private void WriteToFile(String msg) {
        try(FileWriter fw = new FileWriter(HISTORY_FILE_NAME, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(msg);
        } catch (IOException e) {
            System.out.println("Error while writing message to file");
        }
    }

}