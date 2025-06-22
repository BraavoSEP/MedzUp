# 🚀 Guia Completo para Subir o Projeto para o GitHub

Este guia irá ajudá-lo a configurar e subir seu projeto MedzUp para o GitHub desde o início.

## 📋 Pré-requisitos

1. **Conta no GitHub**: Crie uma conta em [github.com](https://github.com)
2. **Git instalado**: Baixe e instale o Git em [git-scm.com](https://git-scm.com)
3. **GitHub CLI (opcional)**: Para facilitar o processo, instale o GitHub CLI

## 🔧 Passo a Passo

### 1. Criar o Repositório no GitHub

#### Opção A: Via GitHub Web
1. Acesse [github.com](https://github.com) e faça login
2. Clique no botão **"+"** no canto superior direito
3. Selecione **"New repository"**
4. Preencha os campos:
   - **Repository name**: `medzup`
   - **Description**: `App de gerenciamento de medicamentos desenvolvido em Kotlin`
   - **Visibility**: Escolha entre Public ou Private
   - **NÃO** marque "Add a README file" (já temos um)
   - **NÃO** marque "Add .gitignore" (já temos um)
   - **NÃO** marque "Choose a license" (já temos um)
5. Clique em **"Create repository"**

#### Opção B: Via GitHub CLI (recomendado)
```bash
# Instalar GitHub CLI primeiro
# Windows: winget install GitHub.cli
# macOS: brew install gh
# Linux: sudo apt install gh

# Login no GitHub
gh auth login

# Criar repositório
gh repo create medzup --public --description "App de gerenciamento de medicamentos desenvolvido em Kotlin"
```

### 2. Configurar Git Localmente

Abra o terminal/PowerShell na pasta do seu projeto e execute:

```bash
# Verificar se o Git está instalado
git --version

# Configurar seu nome e email (substitua pelos seus dados)
git config --global user.name "Seu Nome"
git config --global user.email "seu.email@exemplo.com"

# Inicializar o repositório Git
git init

# Verificar o status
git status
```

### 3. Adicionar Arquivos ao Git

```bash
# Adicionar todos os arquivos (exceto os que estão no .gitignore)
git add .

# Verificar o que foi adicionado
git status

# Fazer o primeiro commit
git commit -m "Initial commit: MedzUp app with multi-language support"
```

### 4. Conectar ao Repositório Remoto

```bash
# Adicionar o repositório remoto (substitua 'seu-usuario' pelo seu username do GitHub)
git remote add origin https://github.com/seu-usuario/medzup.git

# Verificar se foi adicionado corretamente
git remote -v
```

### 5. Enviar para o GitHub

```bash
# Enviar o código para o GitHub
git push -u origin main

# Se der erro de branch, use:
git branch -M main
git push -u origin main
```

## 🔍 Verificações Importantes

### 1. Verificar o .gitignore
Certifique-se de que o arquivo `.gitignore` está funcionando:

```bash
# Verificar se arquivos sensíveis não estão sendo trackeados
git status
```

Você NÃO deve ver arquivos como:
- `local.properties`
- `build/`
- `.gradle/`
- `*.apk`
- `.idea/`

### 2. Verificar Arquivos Importantes
Certifique-se de que estes arquivos estão incluídos:
- `README.md`
- `LICENSE`
- `app/build.gradle.kts`
- `build.gradle.kts`
- `gradle.properties`
- Todos os arquivos `.kt`
- Todos os arquivos de recursos (`res/`)

## 📝 Personalizar o README

Antes de fazer o push final, edite o `README.md`:

1. Substitua `seu-usuario` pelo seu username do GitHub
2. Atualize o nome do autor
3. Adicione informações específicas do seu projeto

## 🚀 Comandos Úteis para o Futuro

```bash
# Verificar status
git status

# Ver mudanças
git diff

# Adicionar mudanças
git add .

# Fazer commit
git commit -m "Descrição das mudanças"

# Enviar para GitHub
git push

# Baixar mudanças do GitHub
git pull

# Ver histórico de commits
git log --oneline
```

## 🔧 Configurações Adicionais

### 1. Configurar Branch Protection (Opcional)
1. Vá para Settings > Branches no seu repositório
2. Adicione uma regra para a branch `main`
3. Marque "Require pull request reviews before merging"

### 2. Configurar GitHub Pages (Opcional)
1. Vá para Settings > Pages
2. Selecione "Deploy from a branch"
3. Escolha a branch `main` e pasta `/docs`

### 3. Adicionar Topics
No seu repositório, clique em "About" e adicione topics como:
- `android`
- `kotlin`
- `jetpack-compose`
- `medicine-reminder`
- `healthcare`

## 🐛 Solução de Problemas

### Erro: "fatal: remote origin already exists"
```bash
git remote remove origin
git remote add origin https://github.com/seu-usuario/medzup.git
```

### Erro: "Permission denied"
```bash
# Configurar autenticação
git config --global credential.helper store
# Na próxima vez que fizer push, digite seu username e token
```

### Erro: "main branch does not exist"
```bash
git branch -M main
git push -u origin main
```

## ✅ Checklist Final

- [ ] Repositório criado no GitHub
- [ ] Git configurado localmente
- [ ] .gitignore funcionando
- [ ] README.md personalizado
- [ ] LICENSE adicionado
- [ ] Código enviado para o GitHub
- [ ] Repositório público e acessível
- [ ] Topics adicionados
- [ ] Descrição do repositório atualizada

## 🎉 Pronto!

Seu projeto MedzUp agora está no GitHub! Você pode compartilhar o link:
`https://github.com/seu-usuario/medzup`

## 📞 Precisa de Ajuda?

Se encontrar algum problema:
1. Verifique se todos os pré-requisitos estão instalados
2. Confirme se o .gitignore está funcionando
3. Verifique se não há arquivos sensíveis sendo enviados
4. Consulte a documentação do Git e GitHub

---

**Dica**: Mantenha seu repositório sempre atualizado fazendo commits regulares com mensagens descritivas! 