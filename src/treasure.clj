(ns treasure)
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
      (with-open [rdr (reader "map.txt")]
        (def noOfRows (count (line-seq rdr)))))

(defn CountColumnsFn []
      (with-open [rdr (reader "map.txt")]
        (doseq [line (line-seq rdr)]
          (def noOfColumns (count line))
          )))

(defn ReadCharactersFromFileFn []
      (with-open [rdr (reader "map.txt")]
        (doseq [line (line-seq rdr)]
          (doseq [character line]
            ;(println character)
            (def charactersVec (conj charactersVec character))
            ))))

(defn ReadFileAsStringFn []
      (def fileContent (slurp "map.txt"))
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
      (let [character (aget maze x y)]
        (if (and  (< x noOfRows) (< y noOfColumns))
          (do
            (if (= (str character) "-")
              true
              false
              )
            )
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
        )


      (if (isSafeFn maze i j)
        (do
          (aset-char sol i j \+)

          ;Move forward in x direction
          (if (solveMazeUtilFn maze (+ i 1) j sol)
            true
            )

          ;If moving in x direction doesn't give solution then Move down in y direction
          (if (solveMazeUtilFn maze i (+ j 1) sol)
            true
            )

          ;If none of the above movements works then BACKTRACK: unmark x, y as part of solution path
          (aset-char sol i j \!)
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
