# Demo Messenger - WebSocket Chat Application

A real-time chat application built with Spring Boot WebSocket featuring topic-based subscriptions and JSON messaging.

## Features
- **Real-time messaging** - Instant message delivery using WebSocket protocol
- **Topic-based subscriptions** - Subscribe to specific chat rooms/topics
- **JSON API** - Structured message format for easy integration  
- **Session management** - Automatic connection handling and cleanup
- **Clean architecture** - Separation of concerns with services and DTOs

## Technology Stack
- Java 17
- Spring Boot 3.0
- WebSocket with custom protocol
- Jackson for JSON processing
- Lombok for boilerplate reduction

## Quick Start

1. **Clone and run the application:**
```bash
git clone https://github.com/platovd/demo-messenger.git
cd demo-messenger
mvn spring-boot:run
```
2. **Open the client interface:**
Navigate to http://localhost:8080 in your browser
3. **Start using the chat:**
- Connect via WebSocket using the interface (`client.html`)
- Subscribe to different topics (chat rooms)
- Send and receive messages in real-time

## Client demonstration
<img width="1632" height="1468" alt="image" src="https://github.com/user-attachments/assets/3531d624-6c9f-4dbd-a198-3560f4facef5" />

