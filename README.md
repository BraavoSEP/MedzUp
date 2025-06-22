# MedzUp - App de Gerenciamento de Medicamentos

## 📱 Sobre o Projeto

MedzUp é um aplicativo Android desenvolvido em Kotlin para ajudar usuários a gerenciar seus medicamentos de forma eficiente. O app oferece lembretes personalizados e histórico de dosagens para múltiplos pacientes.

## ✨ Funcionalidades

- **Gerenciamento de Pacientes**: Adicione e gerencie múltiplos pacientes.
- **Gerenciamento de Medicamentos**: Adicione, edite e remova medicamentos para cada paciente.
- **Lembretes Inteligentes**: Configure horários personalizados para cada medicamento.
- **Histórico de Dosagens**: Acompanhe quando cada dose foi administrada.
- **Interface Moderna**: Design Material 3 com navegação intuitiva.
- **Câmera Integrada**: Escaneie medicamentos (funcionalidade futura)

## 🛠️ Tecnologias Utilizadas

- **Kotlin**: Linguagem principal
- **Jetpack Compose**: UI moderna e declarativa
- **Hilt**: Injeção de dependência
- **Room**: Banco de dados local
- **WorkManager**: Gerenciamento de lembretes em background
- **Navigation Compose**: Navegação entre telas
- **Material Design 3**: Design system

## 📋 Pré-requisitos

- Android Studio Arctic Fox ou superior
- Android SDK 24+
- Kotlin 1.8+
- Gradle 8.0+

## 🚀 Como Executar

1. **Clone o repositório**
   ```bash
   git clone https://github.com/BraavoSEP/MedzUp.git
   cd MedzUp
   ```

2. **Abra no Android Studio**
   - Abra o Android Studio
   - Selecione "Open an existing project"
   - Navegue até a pasta do projeto e selecione

3. **Configure o SDK**
   - Certifique-se de ter o Android SDK instalado
   - Configure o caminho do SDK em `local.properties` (será criado automaticamente)

4. **Execute o projeto**
   - Conecte um dispositivo Android ou inicie um emulador
   - Clique em "Run" (▶️) no Android Studio

## 📁 Estrutura do Projeto

```
app/
├── src/main/
│   ├── java/com/medzup/app/
│   │   ├── data/           # Camada de dados
│   │   │   ├── database/   # Room database
│   │   │   └── repository/ # Repositórios
│   │   ├── di/            # Injeção de dependência
│   │   ├── managers/      # Gerenciadores (idioma, lembretes)
│   │   ├── ui/           # Interface do usuário
│   │   │   ├── screens/  # Telas do app
│   │   │   ├── navigation/ # Navegação
│   │   │   └── theme/    # Tema e cores
│   │   └── workers/      # WorkManager para lembretes
│   └── res/              # Recursos (strings, layouts, etc.)
└── build.gradle.kts      # Configuração do módulo
```

## 🔧 Configurações

### Permissões Necessárias
- **Câmera**: Para escanear medicamentos
- **Notificações**: Para enviar lembretes

### Configurações de Build
- **Target SDK**: 34
- **Minimum SDK**: 24
- **Compile SDK**: 34

## 📱 Telas Principais

1. **Tela de Pacientes**: Lista de pacientes cadastrados.
2. **Tela de Medicamentos do Paciente**: Lista os medicamentos de um paciente específico.
3. **Adicionar Medicamento**: Formulário para cadastrar novo medicamento para um paciente.
4. **Detalhes do Medicamento**: Informações e histórico do medicamento (em desenvolvimento).

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👨‍💻 Autor

**BraavoSEP**
- GitHub: [@BraavoSEP](https://github.com/BraavoSEP)

## 🙏 Agradecimentos

- Material Design 3
- Jetpack Compose
- Android Developer Community

## 📞 Suporte

Se você encontrar algum problema ou tiver sugestões, por favor abra uma [issue](https://github.com/BraavoSEP/MedzUp/issues).

---

⭐ Se este projeto te ajudou, considere dar uma estrela no repositório! 