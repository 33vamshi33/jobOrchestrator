# 🚀 Distributed Job Orchestration System

> **A scalable, resilient, and extensible platform to schedule, orchestrate, and monitor batch and workflow jobs across distributed nodes — built entirely using free, open-source tools.**

---

## 🧩 Key Features

### 🕒 Job Scheduling
- Cron-like expressions
- Manual & immediate triggers
- Delayed execution

### 🔗 Workflow Orchestration
- DAG-based dependency execution
- Conditional branching
- Retry, exponential backoff, timeout handling
- Per-task & global failure policies

### ⚡ Distributed Execution
- Workers pull tasks from Redis Streams
- Job locking to prevent duplicates
- Leader election (future)

### 📊 Monitoring & Logging
- Real-time job status
- Logs & execution history (persisted)
- Prometheus metrics via Spring Boot Actuator

### 🖥️ Web Dashboard & REST API
- Submit jobs, view logs, browse DAGs
- Re-run failed jobs/DAGs
- Lightweight UI (React/HTMX planned)

### 💾 Persistence
- PostgreSQL (Spring Data JPA) for job data/history
- Redis Streams for queues & pub/sub

---

## 🏗️ Architecture

```text
+-------------------+        +----------------------+
|   User/API Client |------->|      Job API         |
+-------------------+        +----------------------+
                                    |
                                    v
                            +------------------+
                            |  Job Scheduler   |<----+
                            +------------------+     |
                                    |                |
                                    v                |
                            +------------------+     |
                            |   DAG Resolver   |     |
                            +------------------+     |
                                    |                |
                            +------------------+     |
                            | Execution Engine |     |
                            +------------------+     |
                                    |                |
                            +------------------+     |
                            |   Job Workers    |     |
                            +------------------+     |
                                    |                |
                                    v                |
+-------------------+        +------------------+    |
| PostgreSQL (Jobs, |<-------| Redis Streams    |<---+
|   DAGs, History)  |        +------------------+
+-------------------+
                                    |
                                    v
+-------------------+        +------------------+
|  Prometheus       |<------>|  Actuator        |
|  Grafana          |        +------------------+
+-------------------+
```

---

## 🗂️ Project Structure

```text
job-orchestrator/
├── src/main/java/com/example/orchestrator/
│   ├── api/             # REST controllers
│   ├── config/          # Spring Boot, Redis, DB configs
│   ├── dag/             # DAG builder, validator, sorter
│   ├── engine/          # Orchestration logic, retry engine
│   ├── executor/        # Shell/HTTP/Docker jobs
│   ├── model/           # JPA entities, DTOs
│   ├── persistence/     # Spring Data JPA repositories
│   ├── queue/           # Redis stream consumer/producer
│   ├── scheduler/       # Scheduler logic
│   └── metrics/         # Micrometer integration
├── resources/
│   └── application.yml  # Configs
├── Dockerfile
├── docker-compose.yml   # Redis, PostgreSQL, Prometheus, Grafana
└── README.md
```

---

## 📦 Tech Stack

| Layer         | Tech Choices                                 |
|---------------|----------------------------------------------|
| Language      | Java 17 (LTS)                                |
| Framework     | Spring Boot 3.x                              |
| Build Tool    | **Gradle**                                   |
| Database      | PostgreSQL (H2 for local dev)                |
| Queue         | Redis Streams                                |
| Scheduler     | Quartz Scheduler (or manual)                 |
| Monitoring    | Micrometer + Prometheus + Grafana            |
| API Docs      | SpringDoc (OpenAPI 3 / Swagger UI)           |
| UI (future)   | HTMX or React + Tailwind                     |
| Deployment    | Docker, Docker Compose                       |
| Testing       | JUnit 5, Testcontainers                      |

---

## 🛠️ Setup & Run Locally

**Prerequisites**
- Java 17+
- Docker + Docker Compose
- IntelliJ IDEA Community Edition
- Redis & PostgreSQL (see docker-compose)

**Clone & Build**
```
git clone https://github.com/yourname/job-orchestrator.git
cd job-orchestrator
./gradlew clean build
```

**Run Services**
```
docker compose up -d  # Starts Redis, PostgreSQL, etc.
```

**Run App**
```
./gradlew bootRun
```

- API docs: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Actuator Metrics: [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)

---

## 🗺️ Roadmap

| Phase   | Features                                                        |
|---------|-----------------------------------------------------------------|
| MVP 1   | REST APIs to register jobs, trigger jobs manually               |
| MVP 2   | Redis queue + job scheduler with basic retry support            |
| MVP 3   | DAG engine with topological sort + conditional branches         |
| MVP 4   | Prometheus metrics, Grafana dashboard, failure reporting        |
| MVP 5   | Workflow logs, history, manual replays                          |
| MVP 6   | Worker node agent, pub/sub job distribution                     |
| MVP 7   | Web dashboard (React/HTMX), job templates                       |
| MVP 8   | Role-based auth, multi-tenant support, audit logging            |
| Phase 9 | CLI client + GitOps integration for job definitions             |
| Phase 10| SaaS-ready: tenant isolation, billing hooks, deployment installer|

---

## 🔒 Security (Future)

- Spring Security for token-based access
- RBAC via DB-stored roles
- Multi-tenant JWT claims support
- Audit logs for every API/job execution

---

## 💼 Monetization & SaaS Potential

| Stream             | Notes                                               |
|--------------------|----------------------------------------------------|
| SaaS Hosted Service| Sell to startups/SMBs                              |
| On-Prem Installer  | Docker Compose or K8s YAML for enterprise          |
| Pro Tier Features  | RBAC, audit logs, custom integrations, SLAs        |
| Template Marketplace| Ready-to-run jobs (DB backup, S3 sync, etc.)      |

---

## ✅ License

MIT or Apache 2.0 — pick before open-sourcing.

---

## 🤝 Contributing

*Coming soon — contribution guide and architecture docs.*

---

## 🙌 Acknowledgements

- Spring Boot
- Redis
- PostgreSQL
- Micrometer
- Prometheus
- Grafana

---

## 🔗 Links

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Redis Streams](https://redis.io/docs/data-types/streams/)
- [Quartz Scheduler](https://www.quartz-scheduler.net/)
- [Micrometer](https://micrometer.io/)
- [Grafana](https://grafana.com/)

---