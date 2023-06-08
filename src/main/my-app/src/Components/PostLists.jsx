import { SimpleGrid } from '@chakra-ui/react';
import { MainCard } from './MainCard';
export const PostLists = () => {
  const dummyList = [
    { posterNo: 0, postListTitle: '인기글' },
    { posterNo: 1, postListTitle: '최신글' },
    { posterNo: 2, postListTitle: '생각글' },
    { posterNo: 3, postListTitle: '일상만화' },
    { posterNo: 4, postListTitle: '나의 보물같은 playList' },
    { posterNo: 5, postListTitle: '어서오슈 방명록' },
  ];
  return (
    <SimpleGrid
      spacing={4}
      templateColumns="repeat(auto-fill, minmax(200px, 1fr))"
    >
      {dummyList.map(item => (
        <MainCard key={item.posterNo} ListTitle={item.postListTitle} PostTitle={item.posterNo} />
      ))}
    </SimpleGrid>
  );
};
