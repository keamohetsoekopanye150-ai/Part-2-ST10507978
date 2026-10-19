
import com.mycompany.MessagePart2.MessagePart2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


public class MessageTestpart2 {

    private MessagePart2 message1;
    private MessagePart2 message2;

    @BeforeEach
    void setUp() {
        MessagePart2.resetAll();

        message1 = new MessagePart2(
                "0012345678", 0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        message2 = new MessagePart2(
                "0112345678", 1, "08575975889",
                "Hi Keegan, did you receive the payment?");
    }

    @Test
    void messageLengthSuccess() {
        assertEquals("Message ready to send.",
                message1.checkMessageLength());
    }

    @Test
    void messageLengthFailure() {
        String longText = "a".repeat(260);

        MessagePart2 tooLong = new MessagePart2(
                "0012345678", 0, "+27718693002", longText);

        assertEquals(
                "Message exceeds 250 characters by 10; please reduce the size.",
                tooLong.checkMessageLength());
    }

    @Test
    void recipientCorrectlyFormatted() {
        assertEquals(
                "Cell phone number successfully captured.",
                message1.checkRecipientCell());
    }

    @Test
    void recipientIncorrectlyFormatted() {
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message2.checkRecipientCell());
    }

    @Test
    void messageHashMessage1() {
        assertEquals("00:0:HITONIGHT",
                message1.createMessageHash());
    }

    @Test
    void messageHashesInLoop() {
        MessagePart2[] messages = {message1, message2};

        String[] expected = {
            "00:0:HITONIGHT",
            "01:1:HI PAYMENT".replace(" ", "")
        };

        for (int i = 0; i < messages.length; i++) {
            assertEquals(expected[i],
                    messages[i].createMessageHash());
        }
    }

    @Test
    void messageIDCreated() {
        MessagePart2 generated = new MessagePart2(
                0, "+27718693002", "Hello there");

        assertTrue(generated.checkMessageID());
        assertEquals(10, generated.getMessageID().length());
    }

    @Test
    void sendMessageOptions() {
        assertEquals("Message successfully sent.",
                message1.sentMessage(1));

        assertEquals("Press 0 to delete the message.",
                message2.sentMessage(2));

        assertEquals("Message successfully stored.",
                message2.sentMessage(3));
    }

    @Test
    void totalMessagesSent() {
        message1.sentMessage(1);
        message2.sentMessage(2);

        assertEquals(1, MessagePart2.returnTotalMessages());
    }

    @Test
    void printMessagesContainsSentMessage() {
        message1.sentMessage(1);

        assertTrue(MessagePart2.printMessages().contains(
                "Hi Mike, can you join us for dinner tonight?"));
    }
}
