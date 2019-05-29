(ns treasurehunt)
(use 'clojure.java.io)

(def noOfRows)
(def noOfColumns)
(def charactersVec [])
(def mazeArray)
(def index 0)
(def goalX)
(def goalY)
(def goalCharacter "@")
(def x 0)
(def y 0)
(def goalFound false)

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
            (def charactersVec (conj charactersVec character))
            ))))

(defn ReadFileAsStringFn []
      (def fileContent (slurp "map1.txt"))
      (println "This is my challenge:")
      (println fileContent)
      )

(defn MapToArrayFn []
      (def mazeArray (make-array Character/TYPE noOfRows noOfColumns))
      (doseq [x (range noOfRows)
              y (range noOfColumns)]
        (let [character (get charactersVec index)]
          (aset mazeArray x y character))
        (def index (+ index 1))))

(defn GetGoalCoordinatesFn []
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
        ;(aset-char solMazeArray x y (aget mazeArray x y))
        )
      )

(defn isSafeFn [maze x y]

      (if (and (< x noOfRows) (>= x 0) (>= y 0) (< y noOfColumns))
        (do
          (let [character (aget maze x y)]
            (if (= (str character) "-")
              true)
            )
          )
        )
      )

(defn SolveMazeUtilFn [maze i j]

      ;if (x, y is goal) return true
      (if (and (= i goalX) (= j goalY))
        (do
          (aset maze i j \+)
          (println "Woo hoo, I found the treasure :-)")
          (def goalFound true)
          (doseq [x (range noOfRows)
                  y (range noOfColumns)]
            (print (aget mazeArray x y))

            (if (= y (- noOfColumns 1))
              (println "")
              )
            )
          )
        )


      (if (= true (isSafeFn maze i j))
        (do
          (aset maze i j \+)
          (SolveMazeUtilFn maze (+ i 1) j)
          (SolveMazeUtilFn maze i (+ j 1))
          (SolveMazeUtilFn maze (- i 1) j)
          (SolveMazeUtilFn maze i (- j 1))
          (aset maze i j \!)
          false
          )
        )
      ;(aset-char sol i j \0)
      ;false
      )


(defn printSolutionMazeFn []
      (doseq [x (range noOfRows)
              y (range noOfColumns)]
        (print (aget mazeArray x y))

        (if (= y (- noOfColumns 1))
          (println "")
          )
        )
      )

(defn MainFn []
      (ReadFileAsStringFn)
      (CountRowsFn)
      (CountColumnsFn)
      (ReadCharactersFromFileFn)
      (MapToArrayFn)
      (GetGoalCoordinatesFn)
      (SolveMazeUtilFn mazeArray x y)
      (if (= false goalFound)
        (println "Uh oh, I could not find the treasure :-(")
        )
      )

(MainFn)

