# Configuração de Idiomas - MedzUp App

## Resumo das Mudanças

Este documento descreve as implementações realizadas para configurar o idioma padrão do app como PT-BR e adicionar suporte à seleção de idioma na primeira inicialização.

## Arquivos Modificados/Criados

### 1. Arquivos de Strings
- `app/src/main/res/values/strings.xml` - Strings em inglês (idioma padrão do sistema)
- `app/src/main/res/values-pt/strings.xml` - Strings em português brasileiro
- `app/src/main/res/values-en/strings.xml` - Strings em inglês (para suporte explícito)

### 2. Gerenciador de Idioma
- `app/src/main/java/com/medzup/app/managers/LanguageManager.kt` - Classe responsável por gerenciar a seleção e persistência do idioma

### 3. Tela de Seleção de Idioma
- `app/src/main/java/com/medzup/app/ui/screens/language_selection/LanguageSelectionScreen.kt` - UI da tela de seleção
- `app/src/main/java/com/medzup/app/ui/screens/language_selection/LanguageSelectionViewModel.kt` - ViewModel da tela

### 4. Configurações do App
- `app/src/main/java/com/medzup/app/MedzUpApplication.kt` - Configuração do idioma padrão
- `app/src/main/java/com/medzup/app/ui/MainActivity.kt` - Lógica de primeira inicialização
- `app/src/main/AndroidManifest.xml` - Configurações de suporte a múltiplos idiomas
- `app/src/main/res/xml/locales_config.xml` - Configuração de idiomas suportados

### 5. Injeção de Dependência
- `app/src/main/java/com/medzup/app/di/ManagersModule.kt` - Módulo para injeção do LanguageManager

## Funcionalidades Implementadas

### 1. Idioma Padrão PT-BR
- O app agora inicia com português brasileiro como idioma padrão
- Configurado na `MedzUpApplication.kt`

### 2. Tela de Seleção de Idioma
- Exibida na primeira inicialização do app
- Permite escolher entre português e inglês
- Interface moderna e intuitiva com Material Design 3

### 3. Persistência da Escolha
- A escolha do idioma é salva nas SharedPreferences
- O idioma selecionado é aplicado em todas as sessões subsequentes

### 4. Suporte Completo a Múltiplos Idiomas
- Todas as strings do app estão traduzidas
- Suporte oficial do Android para mudança de idioma
- Configuração de idiomas suportados no AndroidManifest

## Como Funciona

1. **Primeira Inicialização**: O app verifica se é a primeira vez que está sendo executado
2. **Seleção de Idioma**: Se for a primeira vez, exibe a tela de seleção de idioma
3. **Aplicação do Idioma**: O idioma escolhido é aplicado imediatamente
4. **Persistência**: A escolha é salva e aplicada em todas as próximas inicializações

## Strings Disponíveis

O app agora possui strings organizadas nas seguintes categorias:
- Seleção de idioma
- Navegação
- Tela inicial
- Adicionar medicamento
- Detalhes do medicamento
- Câmera
- Comuns (botões, mensagens)
- Notificações

## Próximos Passos

Para adicionar novos idiomas:
1. Criar pasta `values-[código_do_idioma]` (ex: `values-es` para espanhol)
2. Adicionar arquivo `strings.xml` com as traduções
3. Atualizar `locales_config.xml` com o novo idioma
4. Adicionar opção na tela de seleção de idioma

## Testes

Para testar a funcionalidade:
1. Desinstale o app (para simular primeira inicialização)
2. Instale novamente
3. Verifique se a tela de seleção de idioma aparece
4. Teste a mudança entre português e inglês
5. Reinicie o app para verificar se a escolha foi persistida 