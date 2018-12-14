; Trabalho 2 de Paragidma de Programação Lógica e Funcional
; Nome: Snake
; Aluno: Diogo Alves de Almeida RA: 95108
; Data: 01/12/2017
; Professor: Wagner Igarashi
; Descrição: Esse código foi retirado como base no link https://gist.github.com/antedeguemon/85fe254b5bf7464497fc
; e foi desenvolvido uma solução para que o jogo possa ser resolvido. Há 2 modos: modo jogador e modo IA (onde a solução é rodada).

#lang racket
(require racket/gui/base)
(require rackunit)

; define o tamanho da altura e largura do jogo.
(define BLOCKS_WIDTH 30)
(define BLOCKS_HEIGHT 30)

; define o tamanho em pixels de cada bloco para a criação da janela.
(define BLOCK_SIZE 12)

; defina a posição inicial da cobra, a cobra é uma lista que cada posição dela é uma lista 2 elementos que significa as coordenadas x e y do jogo.
(define cobra (list (list 2 17) (list 1 17)))

; define direção para onde a cobra está indo no momento, inicialmente para a direita.
(define direcao 'cima)

; define a posição onde a comida está, coordenada x = 9 e y = 8.
(define comida (list 9 8))

; define quantas comidas a cobra comeu, quantos pontos o jogador fez.
(define pontos 0)

; define o nome do jogador
(define nome-jogador "")

; define a melhor pontuação, variável que vai definir o recorde do jogo.
(define recorde 0)
(define nome-recorde "")

; mostra pontos e recorde no fim do jogo
(define (mostra-pontos) ( lambda()
                       (printf "Pontos ~a!!\n" pontos)
                       (printf "Recorde ~a!!\n" recorde)))

; pega no terminal o nome do jogador
(define pega-nome ( lambda()
  (printf "Digite seu nome: ")
  (set! nome-jogador (read-line))
  (printf "Bom jogo ~a!!\n" nome-jogador)))


; atualiza o recorde em relação aos pontos.
; funcionamento: é passado como parametro os pontos do jogador no momento e ele verifica se os pontos é maior que o recorde atual,
;                se sim retorna os pontos, senão retorna o recorde.
(define (atualiza_recorde pontos)
  (cond
    [(> pontos recorde) (set! nome-recorde (string-copy nome-jogador)) pontos]
    [else recorde]))

; define IA como booleana setada como falsa por enquanto.
(define IA #f)

; solução para a solução do jogo.
; funcionamento: define um x e y que são a diferença de distancia entre a cabeça da cobra e a comida em relação as coordenadas x e y.
;                Em seguida calcula o valor absoluto para trabalharmos com valores positivos, afinal, não existe distancia negativa. Após calculado
;                o absoluto, é verificado se a distancia maior está no eixo x ou y, caso estiver no eixo x, a cobra deverá andar para a direita ou
;                esquerda, caso esteja no eixo y, deverá andar para cima ou para baixo. Feito isso, deverá ser verificado se o x ou y calculado anteriormente
;                é positivo ou negativo para sabermos a real direção da cobra, ou seja, se x > 0 significa que a comida está à frente da cobra, então
;                ela deverá ir para a esquerda, em seguida é apenas verificado se ela está na direção da direita, pois se ela for para a esquerda imediatamente
;                ela baterá nela mesmo, então é definido que nesse caso ela irá para baixo e depois para a esquerda, evitando esse contato.
(define (anda-sozinho)
  (define x  (- (pega-cabeca 0 cobra) (list-ref comida 0)))
  (define y  (- (pega-cabeca 1 cobra) (list-ref comida 1)))
  (define dif-x (abs x))
  (define dif-y (abs y))
  (cond
   [(> dif-x dif-y)
    (if (> x 0) (cond [(eq? direcao 'direita) 'baixo]
                      [else 'esquerda])
                (cond [(eq? direcao 'esquerda) 'cima]
                      [else 'direita]))]
   [else
    (if (> y 0) (cond [(eq? direcao 'baixo) 'direita]
                      [else 'cima])
                (cond [(eq? direcao 'cima) 'esquerda]
                      [else'baixo]))]))

;************
; move-bloco: inteiro inteiro -> lista.
; funcionamento: remove o último elemento da lista e adiciona um novo no começo dela.
(define (move-bloco x y)
  (reverse (append (cdr (reverse cobra)) (list(list x y)))))

; pega-cabeca o elemento da cabeça.
; funcionamento: pega o elemento no indice indicado do primeiro elemento da lista.
;                exemplo:  (pega-cabeca 1 (list (list 2 17) (list 1 17))) retorna 2.
(define (pega-cabeca posicao lst)
  (list-ref (list-ref lst 0) posicao))
(check-equal? (pega-cabeca 0 (list (list 2 17) (list 1 17)))2)
(check-equal? (pega-cabeca 0 (list (list 15 17) (list 16 17)))15)

; draw-block: dc inteiro inteiro string -> void.
; funcionamento: desenha um bloco no objeto de desenho dado passado por parametro: coordenada x e y e a cor
;                o argumento color pode ser uma string ou um objeto de cor.
(define (draw-block screen x y color)
  (send screen set-brush color 'solid)
  (send screen draw-rectangle (* x BLOCK_SIZE) (* y BLOCK_SIZE) BLOCK_SIZE BLOCK_SIZE))

; move-cobra: void.
; funcionamento: move a cobra para posição dada ('esquerda, 'direita, 'cima ou 'baixo).
(define (move-cobra posicao)
  (case posicao
    ['esquerda (set! cobra (move-bloco (- (pega-cabeca 0 cobra) 1) (pega-cabeca 1 cobra)))]
    ['direita (set! cobra (move-bloco (+ (pega-cabeca 0 cobra) 1) (pega-cabeca 1 cobra)))]
    ['cima (set! cobra (move-bloco (pega-cabeca 0 cobra) (- (pega-cabeca 1 cobra) 1)))]
    ['baixo (set! cobra (move-bloco (pega-cabeca 0 cobra) (+ (pega-cabeca 1 cobra) 1)))]))

; encostou-bloco: lista lista [número] [número] retorna booleano.
; funcionamento: verifica se a lista de pontos comida está na lista cobra
;                g é o índice de exclusão.
(define (encostou-bloco cobra bloco [i 0] [g 666])
  ;(displayln g)
  (if (> (length cobra) i)  ; length (cobra) > i
    (if (and (not (= g i)) (and
      (eq? (list-ref (list-ref cobra i) 0) (list-ref bloco 0)) ; verifica pelo x e pelo y
      (eq? (list-ref (list-ref cobra i) 1) (list-ref bloco 1))))
        #t
      (encostou-bloco cobra bloco (+ i 1) g))
    #f))

; cresce-cobra: void.
; funcionamento: faz a cobra crescer, copiando o último elemento da lista cobra, movendo a cobra para outra posiçao
;                e adicionando o último elemento pego anteriormente.
(define cresce-cobra (lambda ()
  (define x (car (reverse cobra)))
  (set! comida (list (inexact->exact (round (* (random) (- BLOCKS_WIDTH 1)))) (inexact->exact (round (* (random) (- BLOCKS_HEIGHT 1)))) ))
  (move-cobra direcao)
  (set! pontos (add1 pontos))
  (set! recorde (atualiza_recorde pontos))
  (set! cobra (append cobra (list x)))))

; restart: void.
; funcionamento: seta todas as variaveis para o estado original.
(define restart (lambda()
  (set! direcao 'cima)
  (set! comida (list 9 8))
  (set! cobra (list (list 2 17) (list 1 17)))
  (set! pontos 0)
))

; cria a janela para a o jogo.
(define frame (new frame%
  [label "Snake - Trabalho de PPLF"]
  [width (* BLOCKS_WIDTH BLOCK_SIZE)]
  [height (* BLOCKS_HEIGHT BLOCK_SIZE)]))

; canvas-key: -> canvas%.
; funcionamento: função que verifica as teclas apertadas pelo teclado e realiza as ações na interface.
(define (canvas-key frame) (class canvas%
  (define/override (on-char key-event)
    (cond ; todo: in_list? -> set direcao
      [(eq? (send key-event get-key-code) '#\a) (set! direcao 'esquerda)]
      [(eq? (send key-event get-key-code) '#\d) (set! direcao 'direita)]
      [(eq? (send key-event get-key-code) '#\w) (set! direcao 'cima)]
      [(eq? (send key-event get-key-code) '#\s) (set! direcao 'baixo)]
      [(eq? (send key-event get-key-code) '#\1) (set! IA #f) (send comeco stop) (send jogo start 100)]
      [(eq? (send key-event get-key-code) '#\2) (set! IA #t) (send comeco stop) (send jogo start 100)]
      [(eq? (send key-event get-key-code) '#\r) (set! IA #f) (send jogo stop) (restart) (pega-nome) (send comeco start 100)]))
  (super-new [parent frame])))

; atualiza-cobra: -> void
; funcionamento: funções para a atualização da tela, movimentação da cobra, posição da comida.
(define atualiza-cobra (lambda ()
  (cond [IA (set! direcao (anda-sozinho))])
  (draw-block dc (list-ref comida 0) (list-ref comida 1) "red") ; desenha a comida
  (cond [(encostou-bloco cobra comida) (cresce-cobra)] [else (move-cobra direcao)]) ; checa por colisão com a comida
  (send dc draw-text (number->string pontos) (-(* BLOCKS_WIDTH BLOCK_SIZE) 350) 10)
  (for ([block cobra]) (
    if (eq? block (car cobra))
      (draw-block dc (list-ref block 0) (list-ref block 1) "black")
      (draw-block dc (list-ref block 0) (list-ref block 1) "black")))))

; inicio-de-jogo: void.
; funcionamento: textos que aparecerem ao iniciar ou resetar o jogo.
(define inicio-de-jogo (lambda ()
  (send dc draw-text "Olá " (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 145) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 80))
  (send dc draw-text nome-jogador (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 105) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 80))
  (send dc draw-text "Bem vindo ao jogo Snake!" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 145) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 60))
  (cond [(> recorde 0)
         (send dc draw-text "Tente bater o recorde:" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 145) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 40))
         (send dc draw-text "Nome: " (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 145) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 20))
         (send dc draw-text nome-recorde (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 85) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 20))
         (send dc draw-text "Pontos: " (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 145) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 0))
         (send dc draw-text (number->string recorde) (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 65) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 0))
         (send dc draw-text "Selecione seu modo de jogo:" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 145) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) -20))                  
         (send dc draw-text "(1) Modo jogador" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 115) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) -40))
         (send dc draw-text "(2) Modo IA" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 115) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) -60))]
        [else (send dc draw-text "Selecione seu modo de jogo:" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 145) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 40))                  
              (send dc draw-text "(1) Modo jogador" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 115) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 20))
              (send dc draw-text "(2) Modo IA" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 115) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 0))])
))

; fim-de-jogo: void.
; funcionamento: textos que aparecerem ao terminar do jogo.
(define fim-de-jogo (lambda ()
  (send dc draw-text "Fim de jogo" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 90) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 80))
  (send dc draw-text "Jogador:" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 90) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 60))
  (send dc draw-text nome-jogador (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 0) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 60))
  (send dc draw-text "Pontos:" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 90) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 40))
  (send dc draw-text (number->string pontos) (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 0) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 40))
  (send dc draw-text "Recorde:" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 90) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 20))
  (send dc draw-text (number->string recorde) (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 0) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 20))
  (send dc draw-text "Nome recorde:" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 90) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 0))
  (send dc draw-text nome-recorde (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) -45) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 0))
  (send dc draw-text "(r) recomeçar" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 90) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) -20))
))

; instancia o canvas.
(define canvas (
  new (canvas-key frame)))

; pega o drawing canvas.
(define dc (send canvas get-dc))

; seta a fonte.
(send dc set-font (make-object font% 12 'modern))
(send dc set-text-foreground "black")

; manda o objeto frame aparecer.
(send frame show #t)

; loop com colisoes e frames.
; funcionamento: atualiza a tela no tempo definido e verifica as ações do jogador a partir do teclado
;                e se deu fim de jogo caso haja colisão.
(define jogo (new timer%
  [notify-callback (lambda()
    (send dc clear)
    (send dc set-brush "gray" 'solid)
    (send dc draw-rectangle 0 0 (* BLOCKS_WIDTH BLOCK_SIZE) (* BLOCKS_HEIGHT BLOCK_SIZE))
    (define colisao #f)
    (for ([block cobra]
         [j (in-naturals 0)])
      (cond
            [(or (> (list-ref block 0) BLOCKS_WIDTH) (> 0 (list-ref block 0))) (set! colisao #t )]
            [(or (> (list-ref block 1) BLOCKS_HEIGHT) (> 0 (list-ref block 1))) (set! colisao #t)]
            [(eq? #f colisao) (set! colisao (eq? #t (encostou-bloco cobra block 0 j)))]))
    (cond [colisao (fim-de-jogo) (mostra-pontos)] [else (atualiza-cobra)]))]
  [interval #f]))

; chama a ela inicial do jogo.
; funcionamento: inicia o jogo com o fundo definido e o tamanho corretamente, em seguida aparece as mensagens
;                de inicio de jogo.
(define comeco (new timer%
  [notify-callback (lambda()
    (send dc clear)
    (send dc set-brush "gray" 'solid)
    (send dc draw-rectangle 0 0 (* BLOCKS_WIDTH BLOCK_SIZE) (* BLOCKS_HEIGHT BLOCK_SIZE))
    (inicio-de-jogo)
   )]
  [interval #f]))

; inicio do game
(pega-nome)
(send comeco start 100)