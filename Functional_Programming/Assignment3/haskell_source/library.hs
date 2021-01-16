import Data.Char

data LibraryInput = Exit | Error String | Book (String, String) | Author String | Title String

instance Show LibraryInput where
  show Exit = "Exit"
  show (Error xs) = "Invalid Input: " ++ xs
  show (Book (title, author)) = "Book: " ++ title ++ ";" ++ author
  show (Author author) = "Author: " ++ author
  show (Title title) = "Title: " ++ title

trim :: String -> String
trim = f . f
   where f = reverse . dropWhile isSpace
  
parseLibraryInput :: String -> LibraryInput
parseLibraryInput input   | lhs == "Book" = Book ((trim (drop 1 title)), (trim (drop 1 author)))
                          | lhs == "Author" = Author (trim (drop 1 rhs))
                          | lhs == "Title" = Title (trim (drop 1 rhs))
                          | elem (map toLower input) ["q", "e", "exit", "quit"] = Exit
                          | otherwise = (Error input)
                            where
                              (lhs, rhs) = span (/= ':') (trim input)
                              (title, author) = span (/= ';') (trim rhs)


-- exercise:
main :: IO ()
main = do 
  -- task a)
  -- replace with implementation:
  putStrLn "Welcome to your Library"
  library []
  putStrLn "Bye!"
  -- end replace

library :: [(String,String)] -> IO ()
library books =  do
  -- task c)
  -- replace with implementation:

  -- TODO: remove before submit, "print books"
  -- putStrLn "----------------"
  -- print books
  -- putStrLn "----------------"

  -- get input
  input <- getInput
  
  -- define functions
  let 
    isExit (Exit) = True
    isExit _ = False
    
    isError (Error _) = True
    isError _ = False
    getError (Error xs) = xs

    isBook (Book _) = True
    isBook _ = False
    getBook (Book xs) = xs

    isAuthor (Author _) = True
    isAuthor _ = False
    getAuthor (Author xs) = xs

    isTitle (Title _) = True
    isTitle _ = False
    getTitle (Title xs) = xs

    removeBook book [] = []
    removeBook book (x:xs)
      | book == x = xs
      | otherwise = x:(removeBook book xs)

    findBookByAuthor wantedAuthor bookList = 
      [(title, author) | (title, author) <- bookList, author == wantedAuthor ]

    findBookByTitle wantedTitle bookList =
      [(title, author) | (title, author) <- bookList, title == wantedTitle ]

    printBooksFromList [] = 
      do 
        -- putStrLn ""
        return ()
    printBooksFromList (x:xs) =
      do
        putStrLn ( (show (Book x)) ++ ",")
        printBooksFromList xs

  
  -- exit cass
  if isExit input
    then
      return ()
  
  -- error case
  else if isError input
    then
      do
        putStrLn ("There has been an error: " ++ (getError input));
        library books

  -- book case
  else if isBook input
    then
      do
        putStrLn "Do you want to (p)ut the book back or do you want to (t)ake the book?"
        bookPT <- getLine
        -- TODO: why cannot I add here "b <- (getBook input)"
        if bookPT == "p" -- put the book back on the lib
          then
            do
              putStrLn "Done!"
              library ((getBook input):books)     --add book to library

        else if bookPT == "t" -- take the book from the lib
          then
            if (elem (getBook input) books)
              then
                do
                  putStrLn "Done!"
                  library (removeBook (getBook input) books)
            else
              do
                putStrLn "You do not have this book!"
                library books
        else
          do
            putStrLn "Wrong Input!"
            library books

  -- author case
  else if isAuthor input
    then
      do
        putStrLn ("You have the following books from " ++ (getAuthor input))
        printBooksFromList (findBookByAuthor (getAuthor input) books)
        library books
  
  -- title case
  else if isTitle input
    then
      do
        putStrLn ("You have the following books with the title: " ++ (getTitle input))
        printBooksFromList (findBookByTitle (getTitle input) books)
        library books

  else
    return ()
  -- end replace
      

getInput :: IO LibraryInput
getInput = do
  -- task b)
  putStrLn "Would you like to put back or take a book?\n Enter Book: Title's name; Author's name \nAre you looking for an author?\n Enter Author: Author's name \nAre you looking for a special book?\n Enter Title: Title's name."
    -- task b)
  -- replace with implementation:
  putChar '>'
  putChar '\n'
  line <- getLine
  return (parseLibraryInput line)
  -- end replace
