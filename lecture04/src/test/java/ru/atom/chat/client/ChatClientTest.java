package ru.atom.chat.client;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.atom.chat.server.ChatApplication;
import ru.atom.chat.server.ChatController;


import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class ChatClientTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChatController controller;

    @Test
    public void login() throws Exception {
        this.mockMvc.perform(post("/chat/login")
                .param("name", "MyLogin")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void viewChat() throws Exception {
        ChatClient.login("MyLogin");
        this.mockMvc.perform(get("/chat/chat"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[MyLogin] logged in")));
        ChatClient.logout("MyLogin");
    }

    @Test
    public void viewOnline() throws Exception {
        ChatClient.login("MyLogin");
        this.mockMvc.perform(get("/chat/online"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("MyLogin")));
        ChatClient.logout("MyLogin");
    }

    @Test
    public void say() throws Exception {
        ChatClient.login("MyLogin");
        this.mockMvc.perform(post("/chat/say")
                .param("name", "MyLogin")
                .param("msg", "hello"))
                .andExpect(status().isOk());
        ChatClient.logout("MyLogin");
    }


}
