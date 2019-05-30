(ns legacy_treasure)
(use 'clojure.java.io)

(def noOfRows)
(def noOfColumns)
(def charactersVec [])
(def mazeArray)
(def solMazeArray)
(def index 0)
(def goalX)
(def goalY)
(def goalCharacter "@")
(def x 0)
(def y 0)

(defn CountRowsFn []
      (with-open [rdr (reader "map1.txt")]
        (def noOfRows (count (line-seq rdr)))))

(defn CountColumnsFn []
      (with-open [rdr (reader "map1.txt")]
        (doseq [line (line-seq rdr)]
          (def noOfColumns (count line))
          )))

(defn ReadCharactersFromFileFn []
      (with-open [rdr (reader "map1.txt")]
        (doseq [line (line-seq rdr)]
          (doseq [character line]
            ;(println character)
            (def charactersVec (conj charactersVec character))
            ))))

(defn ReadFileAsStringFn []
      (def fileContent (slurp "map1.txt"))
      (println fileContent))

;(defn MapToArrayFn []
;      (let [mazeArray (make-array Character/TYPE noOfRows noOfColumns)]
;        (time (dotimes [i noOfRows]
;                (dotimes [j noOfColumns]
;                  (aset mazeArray i j (get charactersVec index))
;                  (def index (+ index 1))
;                  )))
;        (print mazeArray)
;        ))

(defn MapToArrayFn []
      (def mazeArray (make-array Character/TYPE noOfRows noOfColumns))
      (def solMazeArray (make-array Character/TYPE noOfRows noOfColumns))
      (doseq [x (range noOfRows)
              y (range noOfColumns)]
        (let [character (get charactersVec index)]
          (aset mazeArray x y character))
        (def index (+ index 1))))

(defn RetriveGoalCoordinatesFn []
      (println "____________________")
      (println goalCharacter)
      (doseq [x (range noOfRows)
              y (range noOfColumns)]
        ;(println (aget mazeArray x y))
        (let [character (aget mazeArray x y)]
          (if (= (str character) goalCharacter)
             (do
               (def goalX x)
               (def goalY y)
               )
             )

          )
        )
      )

(defn copyMazeToSolFn []
        (doseq [x (range noOfRows)
                y (range noOfColumns)]
          (aset-char solMazeArray x y (aget mazeArray x y))
        )
      )

(defn isSafeFn [maze x y]

        (if (and (and (< x noOfRows) (< y noOfColumns)) (and (>= x 0) (>= y 0)) )
          (do
            (let [character (aget maze x y)]
            (if (= (str character) "-")
              (do
                (println "True1" x y)
                true
                )
              (do
                (println "False1" x y)
                false
                )

              ))
            )
          (do
            (println "FALSE" x y )
            false
            )
          )

      )


(defn solveMazeUtilFn [maze i j sol]

      ;if (x, y is goal) return true
      (if (and (== i goalX) (== j goalY))
        (do
          (aset-char sol i j \+)
          (println "Found goal")
           true
          )
          (do
            false
            )
        )


      (if (isSafeFn maze i j)
        (do
          (aset-char sol i j \+)

          ;Move forward in North
          (if (solveMazeUtilFn maze (- i 1) j sol)
            (do
              (println "North")
              true
              )
            (do
              false
              )

            )

          ;Move forward in East
          (if (solveMazeUtilFn maze i (+ j 1) sol)
            (do
              (println "East")
              true
              )
            (do
              false
              )
            )

          ;Move forward in South
          (if (solveMazeUtilFn maze (+ i 1) j sol)
            (do
              (println "South")
              true
              )
            (do
              false
              )
            )

          ;Move forward in West
          (if (solveMazeUtilFn maze i (- j 1) sol)
            (do
              (println "West")
              true
              )
            (do
              false
              )
            )

          ;;Move forward in x direction
          ;(if (solveMazeUtilFn maze (+ i 1) j sol)
          ;  true
          ;  )
          ;
          ;;If moving in x direction doesn't give solution then Move down in y direction
          ;(if (solveMazeUtilFn maze i (+ j 1) sol)
          ;  true
          ;  )

          ;If none of the above movements works then BACKTRACK: unmark x, y as part of solution path
          (aset-char sol i j \!)
          false
          )
          (do
            false
            )
        )
      ;(aset-char sol i j \0)
      false
      )


(defn printSolutionMazeFn []
      (doseq [x (range noOfRows)
              y (range noOfColumns)]
        (print (aget solMazeArray x y))

        (if (= y (- noOfColumns 1))
          (println "")
          )
        )
      )

(defn MainFn []
      (ReadFileAsStringFn)
      (CountRowsFn)
      (CountColumnsFn)
      (println noOfRows)
      (println noOfColumns)
      (ReadCharactersFromFileFn)
      (MapToArrayFn)
      (RetriveGoalCoordinatesFn)
      (println goalX)
      (println goalY)
      (copyMazeToSolFn)
      ;(if (solveMazeUtilFn mazeArray x y solMazeArray)
      ;  (do
      ;    (printSolutionMazeFn)
      ;    )
      ;  (do
      ;    (println "Solution not exits")
      ;    )
      ;  )
      (solveMazeUtilFn mazeArray x y solMazeArray)
      (printSolutionMazeFn)
      )

(MainFn)
