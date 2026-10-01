# Especificação do Projecto — Diário de Rede

## 1. Objectivo
Aplicação Android em Java que consulta o estado de conectividade do dispositivo e permite manter um diário com notas manuais.

## 2. Requisitos funcionais
- **RF01:** consultar o estado actual da rede através das APIs Android.
- **RF02:** identificar Wi-Fi.
- **RF03:** identificar dados móveis.
- **RF04:** identificar ausência de ligação.
- **RF05:** permitir escrever e guardar uma nota manual.
- **RF06:** gerar automaticamente a data e hora da nota.
- **RF07:** guardar o tipo de rede detectado no momento da nota.
- **RF08:** mostrar as notas numa lista, da mais recente para a mais antiga.
- **RF09:** manter as notas após fechar e reabrir a aplicação.

## 3. Requisitos técnicos
| Requisito | Implementação |
|---|---|
| Java | Classes de lógica em Java |
| ConstraintLayout | Layouts dos ecrãs |
| Widgets | TextView, Button, TextInputEditText, RecyclerView e MaterialCardView |
| Duas Activities | `MainActivity` e `DiaryActivity` |
| Intent | `MainActivity` abre `DiaryActivity` |
| Rede real | `ConnectivityManager` e `NetworkCapabilities` |
| Permissão | `ACCESS_NETWORK_STATE` no manifesto |
| Notas manuais | Campo de texto e botão Guardar |
| Data/hora | Gerada ao guardar cada nota |
| Lista | `RecyclerView` com adaptador |
| Persistência | `SharedPreferences` com JSON |
| Documentação | README, especificação e checklist |

## 4. Arquitectura
`MainActivity` mostra o estado da rede e regista um `NetworkCallback` para actualizar o ecrã enquanto está aberto. O botão de navegação cria um `Intent` explícito para `DiaryActivity`. Esta Activity valida o texto, gera a data/hora, consulta o estado da rede, cria `NetworkNote`, pede a `NoteStorage` para persistir a nota e actualiza o `RecyclerView`.

## 5. Detecção de rede
1. Obter `ConnectivityManager`.
2. Consultar a rede activa.
3. Consultar as capacidades da rede.
4. Se o transporte for `TRANSPORT_WIFI`, apresentar Wi-Fi.
5. Se for `TRANSPORT_CELLULAR`, apresentar dados móveis.
6. Se não houver rede activa, apresentar sem ligação.
7. Outros transportes são apresentados como tipo desconhecido.

## 6. Permissões
`ACCESS_NETWORK_STATE` é usada para consultar o estado da rede e é concedida automaticamente pelo Android. Não se pede `INTERNET`, pois a aplicação não comunica com servidores.

## 7. Modelo de dados
Cada nota inclui `text`, `dateTime` e `networkType`.

## 8. Testes de aceitação pendentes no dispositivo
- Confirmar Wi-Fi, dados móveis e sem ligação.
- Guardar várias notas e verificar a ordem.
- Confirmar data/hora e tipo de rede em cada nota.
- Fechar e reabrir a aplicação e confirmar persistência.
- Capturar imagens reais de cada cenário para o relatório/repositório.

## 9. Limitações
A persistência é local e simples, feita com `SharedPreferences`/JSON, não com SQLite/Room. O projecto foi revisto estaticamente, mas a compilação e os testes no Android Studio ainda precisam de confirmação.
