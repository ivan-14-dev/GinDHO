gindho/
├── 📂 backend/                                  Spring Boot API
│   ├── src/main/java/com/gindho/
│   │   │
│   │   ├── 📂 config/                           Configuration globale
│   │   │   ├── SecurityConfig.java              Chaîne de filtres Spring Security
│   │   │   ├── JwtConfig.java                   Paramètres JWT (secret, expiration)
│   │   │   ├── AsyncConfig.java                 ThreadPoolTaskExecutor pour @Async
│   │   │   ├── WebSocketConfig.java             Broker STOMP, endpoints SockJS
│   │   │   └── CorsConfig.java                  Origines autorisées par profil
│   │   │
│   │   ├── 📂 controller/                       Couche HTTP — reçoit et répond
│   │   │   ├── AuthController.java              POST /auth/login, /register, /refresh
│   │   │   ├── PatientController.java           CRUD /patients
│   │   │   ├── MedecinController.java           CRUD /medecins + disponibilités
│   │   │   ├── RendezVousController.java        /rendez-vous (prise, annulation…)
│   │   │   ├── DossierController.java           /dossiers + ordonnances
│   │   │   ├── RapportController.java           /rapports/stats, export CSV
│   │   │   ├── UserController.java              /users (permissions secondaires)
│   │   │   └── AuditLogController.java          /audit-logs (admin uniquement)
│   │   │
│   │   ├── 📂 service/                          Couche métier — règles & orchestration
│   │   │   ├── UserService.java                 Auth, création comptes, permissions
│   │   │   ├── PatientService.java              CRUD patients, recherche, export
│   │   │   ├── MedecinService.java              Médecins, agenda, disponibilités
│   │   │   ├── RendezVousService.java           Prise RDV, détection conflits, statuts
│   │   │   ├── DossierMedicalService.java       Dossiers, ordonnances
│   │   │   ├── NotificationService.java         Emails rappels, push WebSocket
│   │   │   ├── RapportService.java              Statistiques, KPIs, exports
│   │   │   └── AuditLogService.java             Traçabilité via AOP
│   │   │
│   │   ├── 📂 dao/                              ★ Couche DAO — contrats d'accès aux données
│   │   │   ├── 📂 interfaces/                   Interfaces publiques (contrats)
│   │   │   │   ├── UserDao.java                 findByEmail(), existsByEmail(), save()…
│   │   │   │   ├── PatientDao.java              findByNumero(), searchByNom(), findAll()…
│   │   │   │   ├── MedecinDao.java              findBySpecialisation(), findDisponibles()…
│   │   │   │   ├── RendezVousDao.java           findConflits(), findByMedecinAndDate()…
│   │   │   │   ├── DossierMedicalDao.java       findByPatient(), findByMedecin()…
│   │   │   │   ├── OrdonnanceDao.java           findByDossier(), findByPatient()…
│   │   │   │   └── AuditLogDao.java             findByUser(), findByEntite()…
│   │   │   │
│   │   │   └── 📂 impl/                         Implémentations JPA concrètes
│   │   │       ├── UserDaoImpl.java             Délègue à UserRepository + logique cache
│   │   │       ├── PatientDaoImpl.java          Délègue à PatientRepository + pagination
│   │   │       ├── MedecinDaoImpl.java          Délègue à MedecinRepository
│   │   │       ├── RendezVousDaoImpl.java       Requête JPQL détection conflits horaires
│   │   │       ├── DossierMedicalDaoImpl.java   Délègue à DossierRepository + @EntityGraph
│   │   │       ├── OrdonnanceDaoImpl.java       Délègue à OrdonnanceRepository
│   │   │       └── AuditLogDaoImpl.java         Délègue à AuditLogRepository
│   │   │
│   │   ├── 📂 repository/                       Couche JPA — Spring Data interfaces
│   │   │   ├── UserRepository.java              extends JpaRepository<User, UUID>
│   │   │   ├── PatientRepository.java           + @Query JPQL personnalisées
│   │   │   ├── MedecinRepository.java           + findBySpecialisationContaining()
│   │   │   ├── RendezVousRepository.java        + requête conflits @Query atomique
│   │   │   ├── DossierMedicalRepository.java    + @EntityGraph pour éviter N+1
│   │   │   ├── OrdonnanceRepository.java        extends JpaRepository<Ordonnance, UUID>
│   │   │   ├── DisponibiliteRepository.java     extends JpaRepository<Disponibilite, UUID>
│   │   │   └── AuditLogRepository.java          extends JpaRepository<AuditLog, UUID>
│   │   │
│   │   ├── 📂 entity/                           Entités JPA — mapping objet-relationnel
│   │   │   ├── BaseEntity.java                  id (UUID), creeLe, misAJourLe (@MappedSuperclass)
│   │   │   ├── User.java                        nom, email, motDePasseHash, Role, actif
│   │   │   ├── RolePermission.java              userId, permission, ressource
│   │   │   ├── Patient.java                     numeroPatient, naissance, groupeSanguin, allergies
│   │   │   ├── Medecin.java                     numeroOrdre, specialisation, disponible
│   │   │   ├── Disponibilite.java               jourSemaine, heureDebut, heureFin
│   │   │   ├── RendezVous.java                  dateHeureDebut/Fin, StatutRDV, motif
│   │   │   ├── DossierMedical.java              diagnostic, traitement, observations
│   │   │   ├── Ordonnance.java                  medicament, posologie, duree
│   │   │   └── AuditLog.java                    action, entite, entiteId, ipAdresse
│   │   │
│   │   ├── 📂 dto/                              Objets de transfert (pas d'entités dans l'API)
│   │   │   ├── 📂 request/                      LoginRequest, CreatePatientRequest…
│   │   │   └── 📂 response/                     TokenResponse, PatientResponse…
│   │   │
│   │   ├── 📂 mapper/                           MapStruct — Entity ↔ DTO
│   │   │   ├── PatientMapper.java
│   │   │   ├── MedecinMapper.java
│   │   │   └── RendezVousMapper.java
│   │   │
│   │   ├── 📂 security/                         Authentification & autorisation
│   │   │   ├── JwtService.java                  Génération, validation, extraction claims
│   │   │   ├── JwtAuthenticationFilter.java     Filtre Bearer Token par requête
│   │   │   └── CustomUserDetailsService.java    Chargement User par email via UserDao
│   │   │
│   │   ├── 📂 scheduler/                        Tâches planifiées (multithreading)
│   │   │   ├── RappelRDVScheduler.java          @Scheduled — rappels email 24h avant RDV
│   │   │   └── SuiviSanteScheduler.java         @Scheduled — rapports hebdomadaires
│   │   │
│   │   ├── 📂 websocket/                        Notifications temps réel
│   │   │   └── NotificationWsController.java    @MessageMapping, broadcast /topic/notifs
│   │   │
│   │   └── 📂 exception/                        Gestion centralisée des erreurs
│   │       ├── GlobalExceptionHandler.java      @RestControllerAdvice
│   │       ├── ConflitHoraireException.java     RDV en conflit (409 Conflict)
│   │       ├── ResourceNotFoundException.java   Entité introuvable (404)
│   │       └── AccessDeniedException.java       Accès refusé (403)
│   │
│   └── src/main/resources/
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-prod.yml
│       └── 📂 db/migration/
│           ├── V1__init_schema.sql              Création de toutes les tables
│           ├── V2__seed_admin.sql               Compte admin par défaut
│           └── V3__add_indexes.sql              Index sur FK et colonnes de recherche
│
├── 📂 javafx-client/                            Application Desktop (Java 21 + JavaFX 21)
│   └── src/main/java/com/gindho/
│       ├── 📂 controllers/                      Contrôleurs FXML
│       │   ├── LoginController.java             Authentification, stockage token
│       │   ├── DashboardController.java         Sidebar dynamique selon rôle
│       │   ├── PatientController.java           TableView patients, formulaires
│       │   ├── MedecinController.java           Agenda, disponibilités
│       │   ├── RendezVousController.java        Calendrier, prise/annulation RDV
│       │   └── DossierController.java           Consultation dossiers médicaux
│       ├── 📂 dao/                              ★ DAO côté JavaFX — appels vers l'API REST
│       │   ├── PatientApiDao.java               GET/POST /api/patients via HttpClient
│       │   ├── MedecinApiDao.java               GET/POST /api/medecins via HttpClient
│       │   ├── RendezVousApiDao.java            GET/POST/PUT /api/rendez-vous
│       │   └── DossierApiDao.java               GET/POST /api/dossiers
│       ├── 📂 services/                         Services techniques JavaFX
│       │   ├── ApiClient.java                   HttpClient centralisé (JWT header auto)
│       │   ├── SessionManager.java              Singleton token + profil utilisateur
│       │   └── WebSocketClient.java             SockJS STOMP — notifications temps réel
│       ├── 📂 models/                           DTOs miroir du backend (Jackson)
│       │   ├── PatientDto.java
│       │   ├── MedecinDto.java
│       │   └── RendezVousDto.java
│       └── src/main/resources/fxml/
│           ├── login.fxml
│           ├── dashboard.fxml
│           ├── patient-list.fxml
│           ├── patient-form.fxml
│           ├── rdv-calendar.fxml
│           └── dossier-detail.fxml
│
├── 📂 react-web/                                Application Web (React 18 + TypeScript)
│   └── src/
│       ├── 📂 api/                              ★ DAO côté React — couche d'accès aux données
│       │   ├── axiosInstance.ts                 Axios configuré (baseURL, intercepteur JWT)
│       │   ├── authApi.ts                       login(), register(), refreshToken()
│       │   ├── patientApi.ts                    getPatients(), createPatient(), getDossier()…
│       │   ├── medecinApi.ts                    getMedecins(), getDisponibilites()…
│       │   ├── rendezVousApi.ts                 createRdv(), annulerRdv(), getRdvs()…
│       │   └── dossierApi.ts                    createDossier(), getOrdonnances()…
│       ├── 📂 pages/                            Vues par rôle
│       │   ├── Login.tsx
│       │   ├── Dashboard.tsx
│       │   ├── Patients.tsx / PatientDetail.tsx
│       │   ├── Medecins.tsx / MedecinDetail.tsx
│       │   ├── RendezVous.tsx
│       │   ├── DossierMedical.tsx
│       │   ├── Rapports.tsx
│       │   └── AuditLogs.tsx
│       ├── 📂 components/                       Composants réutilisables
│       │   ├── Sidebar.tsx / Header.tsx
│       │   ├── PatientCard.tsx
│       │   ├── RDVCalendar.tsx                  FullCalendar wrapper
│       │   └── RDVForm.tsx                      Formulaire avec détection conflit live
│       ├── 📂 hooks/                            Hooks métier (TanStack Query)
│       │   ├── useAuth.ts                       Login, logout, token refresh
│       │   ├── usePatients.ts                   useQuery → patientApi
│       │   ├── useMedecins.ts                   useQuery → medecinApi
│       │   ├── useRendezVous.ts                 useQuery + useMutation → rendezVousApi
│       │   └── useWebSocket.ts                  Connexion STOMP, abonnement topics
│       ├── 📂 store/                            État global (Zustand)
│       │   ├── authStore.ts                     token, user, role, isAuthenticated
│       │   └── notifStore.ts                    notifications non lues, alertes
│       └── 📂 routes/
│           └── ProtectedRoute.tsx               HOC — vérification rôle JWT côté client
│
├── docker-compose.yml
├── docker-compose.prod.yml
└── README.md