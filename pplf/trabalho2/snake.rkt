#lang racket
(require racket/gui)

; tamanho da altura e da largura do jogo
(define BLOCKS_WIDTH 20)
(define BLOCKS_HEIGHT 20)

; tamanho em pixels de cada bloco
(define BLOCK_SIZE 16)

; lista com a posição inicial da cobra
(define cobra (list (list 10 0) (list 11 0)))

; diração para onde a cobra está indo
(define direcao 'direita)

; onde a comida está
(define comida (list 9 8))

; quantas comidas comeu
(define pontos 0)

; define a melhor pontuação
(define recorde 0)

; atualiza o recorde em relação aos pontos
(define (atualiza_recorde pontos)
  (cond
    [(> pontos recorde) pontos]
    [else recorde]))

; define IA como verdadeira
(define IA #f)

; anda sozinho
; ação: verifica a distancia entre a cobra e a comida, em seguida calcula o valor absoluto de x e y.
(define (andando-sozinho)
  (define x  (- (pega-cabeca 0 cobra) (list-ref comida 0)))
  (define y  (- (pega-cabeca 1 cobra) (list-ref comida 1)))
  (define abs-x (abs x))
  (define abs-y (abs y))
  (cond
   [(> abs-x abs-y)
    (if (> x 0) (if (eq? direcao 'direita) 'cima 'esquerda)
                (if (eq? direcao 'esquerda) 'baixo 'direita))]
   [else
    (if (> y 0) (if (eq? direcao 'baixo) 'esquerda 'cima)
                (if (eq? direcao 'cima) 'direita 'baixo))]))


; move-bloco: inteiro inteiro -> lista
; ação: remove o último elemento da lista e adiciona um novo no começo dela
(define (move-bloco x y)
  (reverse (append (cdr (reverse cobra)) (list(list x y)))))

; pega-cabeca: inteiro lista -> ?
; ação: pega o elemento no indice do arugmento posicao do primeiro elemento da lista
; exemplo:  (pega-cabeca 1 (list (list 4 5) (list 6 7))) -> 4
(define (pega-cabeca posicao lst)
  (list-ref (list-ref lst 0) posicao))

; draw-block: dc inteiro inteiro string -> void
; ação: desenha um bloco no objeto de desenho dado
;           o argumento color pode ser uma string ou um objeto de cor
(define (draw-block screen x y color)
  (send screen set-brush color 'solid)
  (send screen draw-rectangle (* x BLOCK_SIZE) (* y BLOCK_SIZE) BLOCK_SIZE BLOCK_SIZE))

; move-cobra: simbolo -> void
; ação: move a cobra para posição dada('esquerda, 'direita, 'cima ou 'baixo)
(define (move-cobra posicao)
  (case posicao
    ['esquerda (set! cobra (move-bloco (- (pega-cabeca 0 cobra) 1) (pega-cabeca 1 cobra)))]
    ['direita (set! cobra (move-bloco (+ (pega-cabeca 0 cobra) 1) (pega-cabeca 1 cobra)))]
    ['cima (set! cobra (move-bloco (pega-cabeca 0 cobra) (- (pega-cabeca 1 cobra) 1)))]
    ['baixo (set! cobra (move-bloco (pega-cabeca 0 cobra) (+ (pega-cabeca 1 cobra) 1)))]))

; encostou-bloco: lista lista [número] [número] -> bool
; ação: verifica se a lista de pontos comida está na lista cobra
;           g é o índice de exclusão
(define (encostou-bloco cobra bloco [i 0] [g 666])
  ;(displayln g)
  (if (> (length cobra) i)  ; length (cobra) > i
    (if (and (not (= g i)) (and
      (eq? (list-ref (list-ref cobra i) 0) (list-ref bloco 0)) ; verifica pelo x e pelo y
      (eq? (list-ref (list-ref cobra i) 1) (list-ref bloco 1))))
        #t
      (encostou-bloco cobra bloco (+ i 1) g))
    #f))

; cresce-cobra: -> void
; ação: faz a cobra crescer, copiando o último elemento da lista cobra, movendo a cobra para outra posiçao
;           e adicionando o último elemento pego anteriormente
(define cresce-cobra (lambda ()
  (define x (car (reverse cobra)))
  (set! comida (list (inexact->exact (round (* (random) (- BLOCKS_WIDTH 1)))) (inexact->exact (round (* (random) (- BLOCKS_HEIGHT 1)))) ))
  (move-cobra direcao)
  (set! pontos (add1 pontos))
  (set! recorde (atualiza_recorde pontos))
  (set! cobra (append cobra (list x)))))

; restart: ->void
; ação: seta todas as variaveis para o estado original
(define restart (lambda()
  (set! direcao 'direita)
  (set! comida (list 9 8))
  (set! cobra (list (list 2 1) (list 1 1)))
  (set! pontos 0)
))

; cria a janela para a cobra
(define frame (new frame%
  [label "Cobra"]
  [width (* BLOCKS_WIDTH BLOCK_SIZE)]
  [height (* BLOCKS_HEIGHT BLOCK_SIZE)]))

; canvas-key: -> canvas%
(define (canvas-key frame) (class canvas%
  (define/override (on-char key-event)
    (cond ; todo: in_list? -> set direcao
      [(eq? (send key-event get-key-code) 'left) (set! direcao 'esquerda)]
      [(eq? (send key-event get-key-code) 'right) (set! direcao 'direita)]
      [(eq? (send key-event get-key-code) 'up) (set! direcao 'cima)]
      [(eq? (send key-event get-key-code) 'down) (set! direcao 'baixo)]
      [(eq? (send key-event get-key-code) '#\i) (set! IA #t) (restart)]
      [(eq? (send key-event get-key-code) '#\r) (set! IA #f) (restart)]))
  (super-new [parent frame])))

; atualiza-cobra: -> void
; ação: funções para a atualização da tela e movimentação da cobra
(define atualiza-cobra (lambda ()
  (cond [IA (set! direcao (andando-sozinho))])
  (draw-block dc (list-ref comida 0) (list-ref comida 1) "red") ; desenha a comida
  (cond [(encostou-bloco cobra comida) (cresce-cobra)] [else (move-cobra direcao)]) ; checa por colisão com a comida
  (send dc draw-text (number->string pontos) (-(* BLOCKS_WIDTH BLOCK_SIZE) 310) 10)
  (for ([block cobra]) (
    if (eq? block (car cobra))
      (draw-block dc (list-ref block 0) (list-ref block 1) "black")
      (draw-block dc (list-ref block 0) (list-ref block 1) "black")))))

; fim-de-jogo: -> void
; ação: eventos que ocorrem ao perder o jogo
(define fim-de-jogo (lambda ()
  (send dc draw-text "Fim de jogo" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 115) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 60))
  (send dc draw-text "Pontos:" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 115) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 40))
  (send dc draw-text (number->string pontos) (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 30) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 40))
  (send dc draw-text "Recorde:" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 115) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 20))
  (send dc draw-text (number->string recorde) (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 20) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 20))
  (send dc draw-text "(i) recomeçar modo IA" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 115) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) 0))
  (send dc draw-text "(r) recomeçar modo jogador" (- (round (/ (* BLOCKS_WIDTH BLOCK_SIZE) 2)) 115) (- (round (/ (* BLOCKS_HEIGHT BLOCK_SIZE) 2)) -20))
))

; instancia o canvas
(define canvas (
  new (canvas-key frame)))

; pega o drawing canvas
(define dc (send canvas get-dc))

; seta a fonte
(send dc set-font (make-object font% 12 'modern))
(send dc set-text-foreground "black")

; manda o objeto frame aparecer
(send frame show #t)

; loop com colisoes e frames
(define timer (new timer%
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
    (if colisao (fim-de-jogo) (atualiza-cobra)))]
  [interval #f]))

(send timer start 100)