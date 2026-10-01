# Diário de Rede — Aplicação Android (Java)

## 1. Identificação do projecto
- **Nome da aplicação:** Diário de Rede
- **Tema:** Diário de Rede
- **Turma:** 3L6LASIR3T
- **Integrantes:**
  1. Nome completo — Allan de Jesus Chambire
  2. Nome completo — Bento Chauque Junior
  3. Nome completo — Elias Eugenio Tuzine
  4. Nome completo — Joao dos Santos
  5. Nome completo - Luis Aires Inhambizo Junior



## 2. Objectivo e funcionalidades
A aplicação apresenta o tipo de ligação de rede detectado no dispositivo e permite registar notas manuais relacionadas com a rede. Identifica **Wi-Fi**, **dados móveis** e **sem ligação**; permite actualizar o estado manualmente e acompanha mudanças de rede enquanto o ecrã principal está aberto. Cada nota recebe automaticamente data, hora e o tipo de rede no momento do registo. As notas são apresentadas da mais recente para a mais antiga e persistem localmente após fechar a aplicação.

## 3. Requisitos técnicos
- Java 17;
- ConstraintLayout nos ecrãs;
- widgets Android/Material, incluindo TextView, Button, TextInputEditText e RecyclerView;
- duas Activities: `MainActivity` e `DiaryActivity`;
- navegação entre Activities por `Intent`;
- consulta real da conectividade com `ConnectivityManager` e `NetworkCapabilities`;
- permissão `ACCESS_NETWORK_STATE`;
- armazenamento local com `SharedPreferences` e JSON;
- documentação do projecto e checklist de validação.

A permissão `INTERNET` não é necessária: a aplicação consulta o estado da rede, mas não faz pedidos a servidores.

## 4. Estrutura
- `MainActivity.java`: ecrã inicial e estado da rede;
- `DiaryActivity.java`: criação e listagem de notas;
- `NetworkNote.java`: modelo de cada nota;
- `NoteAdapter.java`: apresentação da lista;
- `NetworkUtils.java`: detecção da rede;
- `NoteStorage.java`: persistência local;
- `activity_main.xml`, `activity_diary.xml`, `item_note.xml`: interfaces;
- `specification.md`: especificação funcional e técnica.

## 5. Como abrir e executar
1. Extraia o ZIP para uma pasta local.
2. Abra **a pasta raiz `DiarioDeRede`** no Android Studio (a pasta que contém `settings.gradle` e `build.gradle`, não a pasta `app`).
3. Aguarde a sincronização do Gradle. A primeira sincronização pode precisar de Internet para descarregar o Gradle, o plugin Android e as dependências.
4. Instale o Android SDK 35 no SDK Manager, caso não esteja instalado.
5. Seleccione um emulador ou ligue um dispositivo Android com depuração USB.
6. Seleccione a configuração `app` e carregue em **Run (▶)**.
7. Teste com Wi-Fi activo, dados móveis activos e ambas as ligações desligadas.

### Se o Gradle der `SocketTimeoutException`
É uma falha de transferência/sincronização e não prova, por si só, que o código está errado. Confirme a ligação à Internet, desactive temporariamente VPN/proxy se aplicável e tente novamente. Este arquivo não contém o Gradle Wrapper; o projecto depende da configuração de Gradle disponível no Android Studio e do acesso aos repositórios na primeira sincronização.

